/*
 * Copyright 2014-2016 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mygdx.game.desktop.async;

import com.badlogic.gdx.Gdx;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/** @author Kotcrab, mbrlabs */
public abstract class AsyncTask {
    private Thread thread;
    private String threadName;

    private int progressPercent;
    private String message;

    private AsyncTaskListener listener;

    public AsyncTask(String threadName) {
        this.threadName = threadName;

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    execute();
                } catch (final Exception e) {
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            failed(e.getMessage(), e);
                        }
                    });
                }

                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        if (listener != null) listener.finished();
                    }
                });
            }
        };

        thread = new Thread(runnable, threadName);
    }

    public void start() {
        thread.start();
    }

    protected void failed(String reason) {
        if (listener != null) listener.failed(reason);
    }

    protected void failed(String reason, Exception ex) {
        if (listener != null) listener.failed(reason, ex);
    }

    protected abstract void execute() throws Exception;

    /**
     * Executes runnable on OpenGL thread. This methods blocks until runnable
     * finished executing. Note that this will also block main render thread.
     */
    protected void executeOnOpenGL(final Runnable runnable) {
        final CountDownLatch latch = new CountDownLatch(1);

        final AtomicReference<Exception> exceptionAt = new AtomicReference<Exception>();

        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } catch (Exception e) {
                    exceptionAt.set(e);
                }
                latch.countDown();
            }
        });

        try {
            latch.await();

            final Exception e = exceptionAt.get();
            if (e != null) {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        failed(e.getMessage(), e);
                    }
                });
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getProgressPercent() {
        return progressPercent;
    }

    public void setProgressPercent(int progressPercent) {
        this.progressPercent = progressPercent;
        if (listener != null) listener.progressChanged(progressPercent);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                if (listener != null) listener.messageChanged(message);
            }
        });
    }

    public void setListener(AsyncTaskListener listener) {
        this.listener = listener;
    }
}

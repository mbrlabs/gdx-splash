/*
 * Copyright (c) 2017 Marcus Brummer.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mbrlabs.gdxsplash.splash;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.kotcrab.vis.ui.util.async.AsyncTask;
import com.mbrlabs.gdxsplash.assets.TextureAsset;


public class LoadingTask extends AsyncTask {

    private static final int DURATION = 1600;
    private static final int STEP = 50;

    private boolean done;
    private float progress = 0;

    TextureAsset textureAsset;

    public LoadingTask() {
        super("Loading Task");
        textureAsset = new TextureAsset(new FileHandle("badlogic.jpg"));
    }

    @Override
    protected void doInBackground() throws Exception {
        // "load"
        while(progress < DURATION) {
            Thread.sleep(STEP);
            synchronized (this) {
                progress += STEP;
                if(progress > DURATION) progress = DURATION;
            }
        }

        // load from disk
        textureAsset.load();

        // load assets on main thread
        executeOnGdx(() -> {
            textureAsset.glLoad();
            done = true;
        });
    }

    public boolean isDone() {
        return done;
    }

    public synchronized int getProgress() {
        if(progress == 0) return 0;
        return (int)((progress / DURATION) * 100);
    }

    public Texture getTexture() {
        return textureAsset.getAsset();
    }

}

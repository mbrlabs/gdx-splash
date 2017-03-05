package com.mygdx.game.desktop.splash;

import com.badlogic.gdx.graphics.Texture;
import com.kotcrab.vis.ui.util.async.AsyncTask;


public class LoadingTask extends AsyncTask {

    private static final int DURATION = 1600;
    private static final int STEP = 50;

    private Texture texture;

    private boolean done;
    private float progress = 0;

    public LoadingTask() {
        super("Loading Task");
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

        // load assets on main thread
        executeOnGdx(() -> {
            texture = new Texture("badlogic.jpg");
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
        return texture;
    }

}

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

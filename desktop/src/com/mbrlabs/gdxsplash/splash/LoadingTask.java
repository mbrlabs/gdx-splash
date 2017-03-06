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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.util.async.AsyncTask;
import com.mbrlabs.gdxsplash.assets.DummyAsset;
import com.mbrlabs.gdxsplash.assets.TextureAsset;

/**
 * @author Marcus Brummer
 * @version 05-03-2017
 */
public class LoadingTask extends AsyncTask {

    private boolean done;
    private float progress = 0;
    private String message = "";

    private Array<DummyAsset> dummyAssets = new Array<>();
    private TextureAsset textureAsset;

    public LoadingTask() {
        super("Loading Task");
        message = "Collecting assets";
        textureAsset = new TextureAsset(new FileHandle("badlogic.jpg"));
    }

    @Override
    protected void doInBackground() throws Exception {
        // create some dummy assets
        for (int i = 0; i < 30; i++) {
            dummyAssets.add(new DummyAsset("Asset " + i));
        }

        // load dummy assets
        for (DummyAsset asset : dummyAssets) {
            message = "Loading " + asset.getName();
            asset.load();
            executeOnGdx(asset::glLoad);
            progress++;
        }

        // load real texture asset
        message = "Loading " + textureAsset.getName();
        textureAsset.load();
        executeOnGdx(textureAsset::glLoad);
        progress++;

        done = true;
    }

    public boolean isDone() {
        return done;
    }

    public synchronized int getProgress() {
        if (progress == 0) return 0;
        return (int) ((progress / (dummyAssets.size + 1)) * 100);
    }

    public synchronized String getMessage() {
        return message;
    }

    public Texture getTexture() {
        return textureAsset.getAsset();
    }

}

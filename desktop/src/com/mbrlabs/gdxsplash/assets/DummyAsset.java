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

package com.mbrlabs.gdxsplash.assets;

import java.util.Random;

/**
 * @author Marcus Brummer
 * @version 05-03-2017
 */
public class DummyAsset implements Asset<Object> {

    private static final int MAX_LOAD_TIME = 150;
    private static final int MAX_GL_LOAD_TIME = 50;

    private int loadTime;
    private int glLoadTime;

    private Object dummy;
    private String name;

    public DummyAsset(String name) {
        this.name = name;

        final Random rand = new Random();
        this.loadTime = rand.nextInt(MAX_LOAD_TIME);
        this.glLoadTime = rand.nextInt(MAX_GL_LOAD_TIME);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void load() {
        try {
            Thread.sleep(loadTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void glLoad() {
        try {
            Thread.sleep(glLoadTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dummy = new Object();
    }

    @Override
    public Object getAsset() {
        return dummy;
    }

}

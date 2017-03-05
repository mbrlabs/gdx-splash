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

/**
 *
 * @author Marcus Brummer
 * @version 05-03-2017
 */
public class DummyAsset implements Asset<Object> {

    private static final int LOAD_TIME = 50;
    private static final int GL_LOAD_TIME = 10;

    private Object dummy;

    @Override
    public void load() {
        try {
            Thread.sleep(LOAD_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void glLoad() {
        try {
            Thread.sleep(GL_LOAD_TIME);
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

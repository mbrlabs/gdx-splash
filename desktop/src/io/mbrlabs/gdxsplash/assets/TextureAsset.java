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

package io.mbrlabs.gdxsplash.assets;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Marcus Brummer
 * @version 05-03-2017
 */
public class TextureAsset implements Asset<Texture> {

    private FileHandle file;
    private Pixmap pixmap;
    private Texture texture;

    public TextureAsset(FileHandle file) {
        this.file = file;
    }

    @Override
    public String getName() {
        return file.name();
    }

    @Override
    public void load() {
        this.pixmap = new Pixmap(file);
    }

    @Override
    public void glLoad() {
        this.texture = new Texture(this.pixmap);
        pixmap.dispose();
    }

    @Override
    public Texture getAsset() {
        return texture;
    }

}

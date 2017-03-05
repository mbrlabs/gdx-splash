package com.mbrlabs.gdxsplash.assets;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

/**
 *
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

package com.mbrlabs.gdxsplash.assets;

/**
 *
 * @author Marcus Brummer
 * @version 05-03-2017
 */
public interface Asset<T> {

    /**
     * Loading, which can be performed without a OpenGl context (executed async).
     */
    void load();

    /**
     * Loading which requires a OpenGL context (executed on main thread)
     */
    void glLoad();

    /**
     * Returns the loaded asset or null if not loaded.
     * @return  loaded asset or null
     */
    T getAsset();

}

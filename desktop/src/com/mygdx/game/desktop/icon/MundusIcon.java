package com.mygdx.game.desktop.icon;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.desktop.Main;


public class MundusIcon {

    public static void setIcon() {
        try {
            GLFWIconSetter.newInstance().setIcon(Gdx.files.absolute(Main.ICON_CACHE),
                    Gdx.files.internal("icon.ico"), Gdx.files.internal("icon.png"));
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

}

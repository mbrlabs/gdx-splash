package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.desktop.lock.LockFile;
import com.mygdx.game.desktop.lock.LockWindow;
import com.mygdx.game.desktop.splash.SplashScreen;

import java.io.File;

public class Main {

    public static final String REGISTRY = "/home/marcus/.mundus/";
    public static final String LOCK_FILE = REGISTRY + ".lock";
    public static final String ICON_CACHE = REGISTRY + "cache/iconCache/";

    public static void main(String[] arg) {
        // ensure we have a registry
        File registry = new File(REGISTRY);
        if(!registry.exists()) {
            registry.mkdirs();
        }

        // Start Log instance
        if(LockFile.exists()) {
            launchLockWindow();
        } else {
            LockFile.create();
            LockFile.deleteOnExit();
            launchSplashScreen();
        }
    }

    private static void launchSplashScreen() {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Loading Mundus");
        config.setResizable(false);
        config.setWindowedMode(SplashScreen.SCREEN_WIDTH, SplashScreen.SCREEN_HEIGHT);
        config.setDecorated(false);
        config.setWindowPosition(-1, -1);
        new Lwjgl3Application(new SplashScreen(), config);
    }

    private static void launchLockWindow() {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Mundus error");
        config.setResizable(false);
        config.setWindowedMode(LockWindow.WIDTH, LockWindow.HEIGHT);
        config.setWindowPosition(-1, -1);
        new Lwjgl3Application(new LockWindow(), config);
    }

}

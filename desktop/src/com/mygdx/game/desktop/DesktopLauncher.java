package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.desktop.splash.LockWindow;
import com.mygdx.game.desktop.splash.SplashScreen;

import java.io.File;

public class DesktopLauncher {

    public static final String LOCK_FILE = "/home/marcus/.mundus/.lock";

    public static void main(String[] arg) {
        // Start Log instance
        File lockFile = new File(LOCK_FILE);
        if(lockFile.exists()) {
            launchLockWindow();
        } else {
            launchSplashScreen();
        }
    }

    private static void launchSplashScreen() {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Mundus");
        config.setResizable(false);
        config.setWindowedMode(SplashScreen.WIDTH, SplashScreen.HEIGHT);
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

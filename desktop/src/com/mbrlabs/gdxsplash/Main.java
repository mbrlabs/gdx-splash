package com.mbrlabs.gdxsplash;

import java.io.File;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mbrlabs.gdxsplash.lock.LockFile;
import com.mbrlabs.gdxsplash.lock.LockWindow;
import com.mbrlabs.gdxsplash.splash.SplashScreen;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class Main {
    public static final String HOME_DIR = FilenameUtils.concat(FileUtils.getUserDirectoryPath(), ".gdxsplash/");
    public static final String LOCK_FILE = HOME_DIR + ".lock";

    public static void main(String[] arg) {
        // ensure we have a registry
        File home = new File(HOME_DIR);
        if(!home.exists()) {
            home.mkdirs();
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
        config.setTitle("Loading");
        config.setWindowIcon("icon.png");
        config.setResizable(false);
        config.setWindowedMode(SplashScreen.SCREEN_WIDTH, SplashScreen.SCREEN_HEIGHT);
        config.setDecorated(false);
        config.setWindowPosition(-1, -1);
        new Lwjgl3Application(new SplashScreen(), config);
    }

    private static void launchLockWindow() {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Error");
        config.setWindowIcon("icon.png");
        config.setResizable(false);
        config.setWindowedMode(LockWindow.WIDTH, LockWindow.HEIGHT);
        config.setWindowPosition(-1, -1);
        new Lwjgl3Application(new LockWindow(), config);
    }

}

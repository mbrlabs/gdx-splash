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

package com.mbrlabs.gdxsplash;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mbrlabs.gdxsplash.lock.LockFile;
import com.mbrlabs.gdxsplash.lock.LockWindow;
import com.mbrlabs.gdxsplash.splash.SplashScreen;

/**
 * @author Marcus Brummer
 * @version 05-03-2017
 */
public class Main {

    public static final String WINDOW_ICON = "icon.png";

    public static final String LOCK_DIR = FilenameUtils.concat(FileUtils.getUserDirectoryPath(), ".gdxsplash/");
    public static final String LOCK_FILE = LOCK_DIR + ".lock";

    public static void main(String[] arg) {
        // ensure we have a lock folder
        File home = new File(LOCK_DIR);
        if (!home.exists()) {
            home.mkdirs();
        }

        // Start Log instance
        if (LockFile.exists()) {
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
        config.setWindowIcon(WINDOW_ICON);
        config.setResizable(false);
        config.setWindowedMode(SplashScreen.SCREEN_WIDTH, SplashScreen.SCREEN_HEIGHT);
        config.setDecorated(false);
        config.setWindowPosition(-1, -1);
        new Lwjgl3Application(new SplashScreen(), config);
    }

    private static void launchLockWindow() {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Error");
        config.setWindowIcon(WINDOW_ICON);
        config.setResizable(false);
        config.setWindowedMode(LockWindow.WIDTH, LockWindow.HEIGHT);
        config.setWindowPosition(-1, -1);
        new Lwjgl3Application(new LockWindow(), config);
    }

}

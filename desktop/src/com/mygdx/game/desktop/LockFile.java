package com.mygdx.game.desktop;


import org.apache.commons.io.FileUtils;

import java.io.File;


public class LockFile {

    public static final String LOCK_FILE = "/home/marcus/.mundus/.lock";

    public static boolean exists() {
        try {
            return new File(LOCK_FILE).exists();
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public static void deleteOnExit() {
        try {
            File file = new File(LOCK_FILE);
            file.deleteOnExit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void create() {
        try {
            FileUtils.touch(new File(LOCK_FILE));
        } catch (Exception e) {
            e.printStackTrace();
            // TODO do smth
        }
    }

}

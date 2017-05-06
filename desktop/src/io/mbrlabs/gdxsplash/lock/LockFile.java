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

package io.mbrlabs.gdxsplash.lock;

import java.io.File;

import io.mbrlabs.gdxsplash.Main;
import org.apache.commons.io.FileUtils;

/**
 * @author Marcus Brummer
 * @version 05-03-2017
 */
public class LockFile {

    public static boolean exists() {
        try {
            return new File(Main.LOCK_FILE).exists();
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public static void deleteOnExit() {
        try {
            File file = new File(Main.LOCK_FILE);
            file.deleteOnExit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void create() {
        try {
            FileUtils.touch(new File(Main.LOCK_FILE));
        } catch (Exception e) {
            e.printStackTrace();
            // TODO do smth
        }
    }

}

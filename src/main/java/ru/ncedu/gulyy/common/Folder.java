package ru.ncedu.gulyy.common;

import java.io.File;

/**
 * Created by Константин on 22.09.2014.
 * Class for work with folder (directory)
 */
public class Folder {

    /**
     * Recursive method for calculate size of folder
     * @param directory is folder
     * @return folder size
     */
    public static long folderSize(File directory) {
        long length = 0;
        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                length += file.length();
            } else {
                length += folderSize(file);
            }
        }
        return length;
    }
}


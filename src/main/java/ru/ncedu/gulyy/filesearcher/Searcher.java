package ru.ncedu.gulyy.filesearcher;

import java.util.ArrayList;

/**
 * Created by Константин on 16.09.2014.
 */
public interface Searcher {
    /**
     * method search files and directories by part of file name
     * @return list of searching file in special format
     */
    public ArrayList<FileInfo> searchFile();

    /**
     * method search files and directories by regular expression of file name
     * @return list of searching files in special format
     */
    public ArrayList<FileInfo> searchFileByRegular();

    /**
     * method search files and directories by pattern of file name
     * @return list of searching files in special format
     */
    public ArrayList<FileInfo> searchFileByPattern();
}


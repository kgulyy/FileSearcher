package ru.ncedu.gulyy.filesearcher;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import ru.ncedu.gulyy.common.Folder;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Константин on 16.09.2014.
 */
public class FileSearcher implements Searcher {
    private String fileName;
    private ArrayList<FileInfo> filesInfo;
    private File workDirectory;

    public FileSearcher(String fileName) {
        this.fileName = fileName;
        filesInfo = new ArrayList<FileInfo>();
        workDirectory = new File(System.getProperty("user.dir"));
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public ArrayList<FileInfo> searchFile() {
//        delete old list of files
        filesInfo.removeAll(filesInfo);

//        dfs algorithm
        depthFirstSearch(workDirectory);
        return filesInfo;
    }

    @Override
    public ArrayList<FileInfo> searchFileByRegular() {
//        delete old list of files
        filesInfo.removeAll(filesInfo);

//        dfs algorithm
        depthFirstSearchByRegular(workDirectory);
        return filesInfo;
    }

    @Override
    public ArrayList<FileInfo> searchFileByPattern() {
//        delete old list of files
        filesInfo.removeAll(filesInfo);

//        dfs algorithm
        depthFirstSearchByPattern(workDirectory);
        return filesInfo;
    }

    /**
     * @param currentDirectory directory of start DFS
     */
    private void depthFirstSearch(File currentDirectory) {
        File[] correctFiles = currentDirectory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().contains(fileName);
            }
        });

        //Use functional approach
        Collection<FileInfo> gatheredInfo = Collections2.transform(Arrays.asList(correctFiles), new Function<File, FileInfo>() {
            @Override
            public FileInfo apply(File file) {
                return gatherFileInfo(file);
            }
        });

        filesInfo.addAll(gatheredInfo);
        //addGatherFiles(correctFiles);

        File[] files = currentDirectory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                depthFirstSearch(file);
            }
        }
    }

    private void depthFirstSearchByRegular(File currentDirectory) {
        File[] correctFiles = currentDirectory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                Pattern pattern = Pattern.compile(fileName);
                Matcher matcher = pattern.matcher(name);
                return matcher.matches();
            }
        });
        addGatherFiles(correctFiles);


        File[] files = currentDirectory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                depthFirstSearchByRegular(file);
            }
        }
    }

    private void depthFirstSearchByPattern(File currentDirectory) {
        File[] correctFiles = currentDirectory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                String stringPattern = fileName.replaceAll("\\?", ".?");
                stringPattern = stringPattern.replaceAll("\\*", ".*");
                Pattern windowsPattern = Pattern.compile(stringPattern);
                Matcher matcher = windowsPattern.matcher(name);
                return matcher.matches();
            }
        });
        addGatherFiles(correctFiles);

        File[] files = currentDirectory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                depthFirstSearchByPattern(file);
            }
        }
    }

    /**
     * method add correct files from array to list
     *
     * @param files array of correct files
     */
    private void addGatherFiles(File[] files) {
        for (File file : files) {
            filesInfo.add(gatherFileInfo(file));
        }
    }

    /**
     * Method collect info from file
     *
     * @param file file or directory
     * @return instance of FileInfo
     */
    private FileInfo gatherFileInfo(File file) {
        if (file.isFile()) {
            return new FileInfo(true, file.getName(), file.toPath().getParent().toString(), file.length(), file.lastModified());
        }
        return new FileInfo(false, file.getName(), file.toPath().getParent().toString(), Folder.folderSize(file), file.lastModified());
    }
}


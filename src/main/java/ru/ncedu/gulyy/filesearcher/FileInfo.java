package ru.ncedu.gulyy.filesearcher;

import java.text.SimpleDateFormat;

/**
 * Created by Константин on 16.09.2014.
 */
public class FileInfo {
    private boolean isFile;
    private String fileName;
    private String filePath;
    private long fileLength;
    private long lastModifiedDate;

    public FileInfo(boolean isFile, String fileName, String filePath, long fileLength, long lastModifiedDate) {
        this.isFile = isFile;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileLength = fileLength;
        this.lastModifiedDate = lastModifiedDate;
    }

    private String dateToString(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }

    public static String printTableHeading() {
        return String.format("%-7s%-25s%-100s", "isFile", "Name", "Path");
    }

    @Override
    public String toString() {
        /*return "{" +
                "isFile=" + isFile +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileLength=" + fileLength + " byte" +
                ", lastModifiedDate='" + dateToString(lastModifiedDate) + '\'' +
                '}';
        */
        return String.format("%-7s%-25s%-100s", isFile, fileName, filePath);
    }
}

import org.junit.Assert;
import org.junit.Test;
import ru.ncedu.gulyy.filesearcher.FileSearcher;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Константин on 25.11.2014.
 */
public class FileSearcherTest {
    private int filesCount; // number of searching files

    /**
     * Search for not-existent files or directories
     * by part of file name
     */
    @Test
    public void test_1() {
        FileSearcher fileSearcher = new FileSearcher("abrakadabra");
        ArrayList result = fileSearcher.searchFile();
        if (result.size() != 0) {
            Assert.fail();
        }
    }

    /**
     * Search for not-existent files or directories
     * by regular expression of file name
     */
    @Test
    public void test_2() {
        FileSearcher fileSearcher = new FileSearcher("abra.*kadabra");
        ArrayList result = fileSearcher.searchFileByRegular();
        if (result.size() != 0) {
            Assert.fail();
        }
    }

    /**
     * Search for not-existent files or directories
     * by pattern of file name
     */
    @Test
    public void test_3() {
        FileSearcher fileSearcher = new FileSearcher("*r?kad?bra");
        ArrayList result = fileSearcher.searchFileByPattern();
        if (result.size() != 0) {
            Assert.fail();
        }
    }

    /**
     * Search for all files and folders in the working directory
     * by part of file name
     */
    @Test
    public void test_4() {
        FileSearcher fileSearcher = new FileSearcher("");
        int result = fileSearcher.searchFile().size();

        Assert.assertEquals("Count of searching files mistake", getCountFiles(), result);
    }

    /**
     * Search for all files and folders in the working directory
     * by regular expression of file name
     */
    @Test
    public void test_5() {
        FileSearcher fileSearcher = new FileSearcher(".*");
        int result = fileSearcher.searchFileByRegular().size();

        Assert.assertEquals("Count of searching files mistake",getCountFiles(), result);
    }

    /**
     * Search for all files and folders in the working directory
     * by pattern of file name
     */
    @Test
    public void test_6() {
        FileSearcher fileSearcher = new FileSearcher("*");
        int result = fileSearcher.searchFileByPattern().size();

        Assert.assertEquals("Count of searching files mistake", getCountFiles(), result);
    }

    /**
     * Counting number of files
     * @return number of files
     */
    private int getCountFiles() {
        File workDirectory = new File(System.getProperty("user.dir"));
        filesCount = 0;
        depthSearchFiles(workDirectory);
        return filesCount;
    }

    /**
     * Depth-first search of files
     * @param currentDirectory
     */
    private void depthSearchFiles(File currentDirectory) {
        File[] files = currentDirectory.listFiles();
        for (File file : files) {
            filesCount++;
            if (file.isDirectory()) {
                depthSearchFiles(file);
            }
        }
    }

    /**
     * Search for "index" files of 3 solve
     */
    @Test
    public void test_7() {

        FileSearcher fileSearcher = new FileSearcher("index");
        int result1 = fileSearcher.searchFile().size();

        fileSearcher.setFileName("index.*");
        int result2 = fileSearcher.searchFileByRegular().size();

        fileSearcher.setFileName("index*");
        int result3 = fileSearcher.searchFileByPattern().size();

        Assert.assertEquals("Search for \"index\" mistake 1", result1, result2);
        Assert.assertEquals("Search for \"index\" mistake 2", result1, result3);
        Assert.assertEquals("Search for \"index\" mistake 3", result2, result3);
    }

    /**
     * Search for html documents files
     */
    @Test
    public void test_8() {
        FileSearcher fileSearcher = new FileSearcher(".html");
        int result1 = fileSearcher.searchFile().size();

        fileSearcher.setFileName(".*\\.html");
        int result2 = fileSearcher.searchFileByRegular().size();

        fileSearcher.setFileName("*.html");
        int result3 = fileSearcher.searchFileByPattern().size();

        Assert.assertEquals("Search for html documents files mistake 1", result1, result2);
        Assert.assertEquals("Search for html documents files mistake 2", result1, result3);
        Assert.assertEquals("Search for html documents files mistake 3", result2, result3);
    }

    /**
     * Search for particular file
     */
    @Test
    public void test_9() {
        FileSearcher fileSearcher = new FileSearcher("index.js");
        int result1 = fileSearcher.searchFile().size();

        fileSearcher.setFileName("index.js");
        int result2 = fileSearcher.searchFileByRegular().size();

        fileSearcher.setFileName("index.js");
        int result3 = fileSearcher.searchFileByPattern().size();

        Assert.assertEquals("Search for particular file mistake 1", result1, result2);
        Assert.assertEquals("Search for particular file mistake 2", result1, result3);
        Assert.assertEquals("Search for particular file mistake 3", result2, result3);
    }

    /**
     * Search for files and folders associated with "php"
     */
    @Test
    public void test_10() {
        FileSearcher fileSearcher = new FileSearcher("php");
        int result1 = fileSearcher.searchFile().size();

        fileSearcher.setFileName(".*php.*");
        int result2 = fileSearcher.searchFileByRegular().size();

        fileSearcher.setFileName("*php*");
        int result3 = fileSearcher.searchFileByPattern().size();

        Assert.assertEquals("Search for files and folders associated with \"php\" mistake 1", result1, result2);
        Assert.assertEquals("Search for files and folders associated with \"php\" mistake 2", result1, result3);
        Assert.assertEquals("Search for files and folders associated with \"php\" mistake 3", result2, result3);
    }

    /**
     * Search for null part of file name
     */
    @Test
    public void test_11() {
        FileSearcher fileSearcher = new FileSearcher("");
        int result1 = fileSearcher.searchFile().size();
        Assert.assertEquals("Search by part for null file name mistake", getCountFiles(), result1);

        int result2 = fileSearcher.searchFileByRegular().size();
        Assert.assertEquals("Search by regular expression for null file name mistake", 0, result2);

        int result3 = fileSearcher.searchFileByPattern().size();
        Assert.assertEquals("Search by pattern for null file name mistake", 0, result3);
    }
}

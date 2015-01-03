import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import ru.ncedu.gulyy.filesearcher.*;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Константин on 25.11.2014.
 */
public class FileSearcherTest {
    private int filesCount; // number of searching files

    //It is better to use interface and provide concrete realization in @Before method
    private FilesSearcher searcher;

    @After
    public void after() {
        searcher = null;
    }

    /**
     * Search for existent files or directories
     * by part of file name
     */
    @Test
    public void test_0() {
        searcher = new SimpleFilesSearcher("abrakadabra0");
        ArrayList<FileInfo> result = searcher.searchFiles();
        if (result.size() == 0) {
            Assert.fail();
        }
    }

    /**
     * Search for not-existent files or directories
     * by part of file name
     */
    @Test
    public void test_1() {
        searcher = new SimpleFilesSearcher("abrakadabra1");
        //TODO Don't use List without they parameters!
        ArrayList<FileInfo> result = searcher.searchFiles();
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
        searcher = new RegularFilesSearcher("abra.*kadabra");
        ArrayList<FileInfo> result = searcher.searchFiles();
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
        searcher = new PatternFilesSearcher("*r?kad?bra");
        ArrayList<FileInfo> result = searcher.searchFiles();
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
        searcher = new SimpleFilesSearcher("");
        int result = searcher.searchFiles().size();

        Assert.assertEquals("Count of searching files mistake", getCountFiles(), result);
    }

    /**
     * Search for all files and folders in the working directory
     * by regular expression of file name
     */
    @Test
    public void test_5() {
        searcher = new RegularFilesSearcher(".*");
        int result = searcher.searchFiles().size();

        Assert.assertEquals("Count of searching files mistake",getCountFiles(), result);
    }

    /**
     * Search for all files and folders in the working directory
     * by pattern of file name
     */
    @Test
    public void test_6() {
        searcher = new PatternFilesSearcher("*");
        int result = searcher.searchFiles().size();

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

        FilesSearcher searcher1 = new SimpleFilesSearcher("index");
        int result1 = searcher1.searchFiles().size();

        FilesSearcher searcher2 = new RegularFilesSearcher("index.*");
        int result2 = searcher2.searchFiles().size();

        FilesSearcher searcher3 = new PatternFilesSearcher("index*");
        int result3 = searcher3.searchFiles().size();

        Assert.assertEquals("Search for \"index\" mistake 1", result1, result2);
        Assert.assertEquals("Search for \"index\" mistake 2", result1, result3);
        Assert.assertEquals("Search for \"index\" mistake 3", result2, result3);
    }

    /**
     * Search for html documents files
     */
    @Test
    public void test_8() {
        FilesSearcher searcher1 = new SimpleFilesSearcher(".html");
        int result1 = searcher1.searchFiles().size();

        FilesSearcher searcher2 = new RegularFilesSearcher(".*\\.html");
        int result2 = searcher2.searchFiles().size();

        FilesSearcher searcher3 = new PatternFilesSearcher("*.html");
        int result3 = searcher3.searchFiles().size();

        Assert.assertEquals("Search for html documents files mistake 1", result1, result2);
        Assert.assertEquals("Search for html documents files mistake 2", result1, result3);
        Assert.assertEquals("Search for html documents files mistake 3", result2, result3);
    }

    /**
     * Search for particular file
     */
    @Test
    public void test_9() {
        FilesSearcher searcher1 = new SimpleFilesSearcher("index.js");
        int result1 = searcher1.searchFiles().size();

        FilesSearcher searcher2 = new RegularFilesSearcher("index.js");
        int result2 = searcher2.searchFiles().size();

        FilesSearcher searcher3 = new PatternFilesSearcher("index.js");
        int result3 = searcher3.searchFiles().size();

        Assert.assertEquals("Search for particular file mistake 1", result1, result2);
        Assert.assertEquals("Search for particular file mistake 2", result1, result3);
        Assert.assertEquals("Search for particular file mistake 3", result2, result3);
    }

    /**
     * Search for files and folders associated with "php"
     */
    @Test
    public void test_10() {
        FilesSearcher searcher1 = new SimpleFilesSearcher("php");
        int result1 = searcher1.searchFiles().size();

        FilesSearcher searcher2 = new RegularFilesSearcher(".*php.*");
        int result2 = searcher2.searchFiles().size();

        FilesSearcher searcher3 = new PatternFilesSearcher("*php*");
        int result3 = searcher3.searchFiles().size();

        Assert.assertEquals("Search for files and folders associated with \"php\" mistake 1", result1, result2);
        Assert.assertEquals("Search for files and folders associated with \"php\" mistake 2", result1, result3);
        Assert.assertEquals("Search for files and folders associated with \"php\" mistake 3", result2, result3);
    }

    /**
     * Search for null part of file name
     */
    @Test
    public void test_11() {
        FilesSearcher searcher1 = new SimpleFilesSearcher("");
        int result1 = searcher1.searchFiles().size();
        Assert.assertEquals("Search by part for null file name mistake", getCountFiles(), result1);

        FilesSearcher searcher2 = new RegularFilesSearcher("");
        int result2 = searcher2.searchFiles().size();
        Assert.assertEquals("Search by regular expression for null file name mistake", 0, result2);

        FilesSearcher searcher3 = new PatternFilesSearcher("");
        int result3 = searcher3.searchFiles().size();
        Assert.assertEquals("Search by pattern for null file name mistake", 0, result3);
    }
}

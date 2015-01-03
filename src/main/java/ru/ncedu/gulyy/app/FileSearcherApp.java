package ru.ncedu.gulyy.app;

import ru.ncedu.gulyy.filesearcher.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Константин on 16.09.2014.
 */
public class FileSearcherApp {

    public static void run() throws IOException {

        while(true) {
            printWelcomeMessage();

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String menuPoint = br.readLine();

            caseMenuPoint(menuPoint, br);

            waitClick(br);
        }
    }

    private static void printWelcomeMessage() {
        System.out.println("Menu files(directories) search:");
        System.out.println("1. Search by part of the file name");
        System.out.println("2. Regular expression search");
        System.out.println("3. Search using symbols * and ?");
        System.out.println("0. Exit");
        System.out.print("Please enter a menu item: ");
    }

    private static void caseMenuPoint(String menuPoint, BufferedReader br) throws IOException {
        /*try {
            caseValue = Integer.valueOf(menuPoint);
        } catch (IllegalArgumentException e) {
            System.out.println("Error! Incorrect input.");
        }*/
        if (menuPoint.equals("0")) {
            System.exit(0);

        } else if (menuPoint.equals("1")) {
            System.out.println("Enter part of the file name: ");
            String partFileName = br.readLine();
            FilesSearcher searcher = new SimpleFilesSearcher(partFileName);
            printFileInfo(searcher.searchFiles());

        } else if (menuPoint.equals("2")) {
            System.out.print("Enter regular expression: ");
            String fileName = br.readLine();
            FilesSearcher searcher = new RegularFilesSearcher(fileName);
            try {
                printFileInfo(searcher.searchFiles());
            } catch (Exception e) {
                System.out.println("Error! Incorrect regular expression.");
            }

        } else if (menuPoint.equals("3")) {
            System.out.print("Enter search pattern: ");
            String fileName = br.readLine();
            FilesSearcher searcher = new PatternFilesSearcher(fileName);
            printFileInfo(searcher.searchFiles());

        } else {
            System.out.println("Error! Incorrect input.");
        }
    }

    private static void printFileInfo(ArrayList<FileInfo> files) {
        if (files.size() == 0) {
            System.out.println("Unfortunately, your search did not match");
        } else {
            System.out.println();
            System.out.print("Search Results: ");
            System.out.println(files.size());
            System.out.println(FileInfo.printTableHeading());
            System.out.println("----------------------------------------------------------------------------------------");
            for (FileInfo fi : files) {
                System.out.println(fi);
            }
        }
    }

    private static void waitClick(BufferedReader br) throws IOException {
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.print("Enter any character to continue: ");
        br.readLine();
        System.out.println();
    }
}


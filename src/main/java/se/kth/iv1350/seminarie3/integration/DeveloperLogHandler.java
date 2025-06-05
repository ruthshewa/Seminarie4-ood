package se.kth.iv1350.seminarie3.integration;

import se.kth.iv1350.seminarie3.integration.DatabaseFailureException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Handles logging of exceptions for developers by writing them to a file.
 */
public class DeveloperLogHandler {
    private static final String FILE_NAME = "Exceptions.txt";
    private PrintWriter ToDeveloper;

    /**
     * Starts a new logger that writes to the file Exceptions.txt.
     * 
     * @throws DatabaseFailureException If the file can't be opened.
     */
    public DeveloperLogHandler() {

        try {

            ToDeveloper = new PrintWriter(new FileWriter(FILE_NAME, true), true);
        } catch (Exception e) {
            throw new DatabaseFailureException(
                "Failed to open the devlog file: " + FILE_NAME
            );
        }
    }

    /**
     * Logs the given exception to the log file.
     *
     * @param e The exception to log.
     */
    public void logException(Exception e) {
        ToDeveloper.println("Exception was thrown: " + e.getMessage());
        e.printStackTrace(ToDeveloper);
    }
}


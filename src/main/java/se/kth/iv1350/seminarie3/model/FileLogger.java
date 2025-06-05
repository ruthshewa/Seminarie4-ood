package se.kth.iv1350.seminarie3.model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class writes messages to a file for logging.
 * It is used to save information about events or errors, to a text file.
 */
public class FileLogger {
    private PrintWriter logStream;

    /**
     * Creates a logger that writes to a file called nunu.txt.
     * If the file cannot be opened, an error message is printed.
     */
    public FileLogger() {
        String fileName = "nunu.txt";
        try {
            logStream = new PrintWriter(new FileWriter(fileName, true), true);
        } catch (IOException e) {
            System.out.println("[ERROR] Cannot open nunu.txt for logging.");
            e.printStackTrace();
        }
    }

    /**
     * Writes a message to the log file.
     *
     * @param message The message to write.
     */
    public void log(String message) {
        logStream.println(message);
    }
}

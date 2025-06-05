package se.kth.iv1350.seminarie3.model;

import junit.framework.TestCase;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
* Tests for the FileLogger class using JUnit 3.
*/
public class FileLoggerTest extends TestCase {

    private static final String FILE_NAME = "nunu.txt";

    public void testLogWritesMessageToFile() throws IOException {
        FileLogger logger = new FileLogger();
        String testMessage = "JUnit test log entry";
        logger.log(testMessage);
        boolean found = false;
        BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.equals(testMessage)) {
                found = true;
                break;
            }
        }
        reader.close();
        assertTrue("The test message should be found in the log file.", found);
    }
}

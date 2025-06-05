package se.kth.iv1350.seminarie3.integration;

import junit.framework.TestCase;

/**
* This class tests the DatabaseFailureException to make sure
* it behaves correctly. It checks if the exception stores messages properly
* and if it is of the correct type, a RuntimeException.
*/
public class DatabaseFailureExceptionTest extends TestCase {

    public void testExceptionMessage() {
        String errorMessage = "Database connection failed!";
        DatabaseFailureException exception = new DatabaseFailureException(errorMessage);

        /**
        * Tests that the exception stores and returns the message it was created with.
        */
        assertEquals(
            "The exception message should match the input.",
            errorMessage,
            exception.getMessage()
        );
    }

    /**
    * Tests that the exception is a subtype of RuntimeException.
    */
    public void testExceptionIsRuntimeException() {
        DatabaseFailureException exception = new DatabaseFailureException("Error");

        // In JUnit 3, assertTrue takes (message, condition)
        assertTrue(
            "Exception should be a subtype of RuntimeException.",
            exception instanceof RuntimeException
        );
    }
}

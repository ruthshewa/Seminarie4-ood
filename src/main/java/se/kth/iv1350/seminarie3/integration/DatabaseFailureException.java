package se.kth.iv1350.seminarie3.integration;

import java.lang.Exception;

/**
 * Thrown when there is a failure in the database, such as a connection problem.
 */
public class DatabaseFailureException extends RuntimeException {
    

    /**
     * Constructs a meessage explaining the rootcause behind the Exception.
     *
     * @param message A message describing the failure of the database.
     */
    public DatabaseFailureException( String message) {
        super(message);
        
    }

    
}


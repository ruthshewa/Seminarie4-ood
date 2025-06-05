package se.kth.iv1350.seminarie3.model;

/**
 * This exception is thrown when an item or ID is not found or is invalid.
 */
public class InvalidIdentifierException extends Exception{
    
    /**
     * Instantiates the exception with a message that describes the problem.
     *
     * @param msg contains the decription of the error message.
     */
    public InvalidIdentifierException(String msg){
        super(msg);
    }
}

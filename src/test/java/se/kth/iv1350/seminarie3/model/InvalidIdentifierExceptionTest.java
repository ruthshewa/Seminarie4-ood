package se.kth.iv1350.seminarie3.model;
import se.kth.iv1350.seminarie3.integration.ExternalInventorySystem;
import junit.framework.TestCase;

/**
* Tests for the InvalidIdentifierException class using JUnit 3.
* Verifies that the exception stores the correct message and is thrown
* as expected when an invalid item ID is used in the inventory system.
*/
public class InvalidIdentifierExceptionTest extends TestCase {

    /**
    * Tests that the exception stores and returns the correct message
    * passed when it is created.
    */
    public void testExceptionMessage() {
        String expectedMessage = "Invalid item ID!";
        InvalidIdentifierException exception = new InvalidIdentifierException(expectedMessage);
        assertEquals("The exception should return the correct message.",
                expectedMessage, exception.getMessage());
    }

    /**
    * Tests that the ExternalInventorySystem 
    * InvalidIdentifierException when an invalid item ID is used.
    */
    public void testExceptionThrownWhenItemNotFound() {
        ExternalInventorySystem inventory = new ExternalInventorySystem();
        String badId = "nonexistent_id";
        try {
            inventory.findItemUsingId(badId);
            fail("Expected InvalidIdentifierException was not thrown.");
        } catch (InvalidIdentifierException e) {
            assertEquals("The itemID: " + badId + " is INVALID RUTH", e.getMessage());
        }
    }
} 
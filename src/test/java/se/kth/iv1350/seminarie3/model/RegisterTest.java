package se.kth.iv1350.seminarie3.model;

import junit.framework.TestCase;

/**
* Unit tests for the Register class using JUnit 3.
*/
public class RegisterTest extends TestCase {

    /**
    * Tests that a new register is created successfully and is not null.
    */
    public void testRegisterCreation() {
        Register register = new Register();
        assertNotNull(register);
    }

    /**
    * Tests that the paid amount is correctly added to the register's internal balance.
    * Uses reflection to access the private balance field for verification.
    */
    public void testAmountIsAddedToBalance() {
        Register register = new Register();
        double amount = 150.0;
        register.amountPaidAddedToRegister(amount);

        try {
            java.lang.reflect.Field field = Register.class.getDeclaredField("balance");
            field.setAccessible(true);
            double actualBalance = field.getDouble(register);
            assertEquals(amount, actualBalance, 0.0001);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Could not access the balance field using reflection.");
        }
    }
}

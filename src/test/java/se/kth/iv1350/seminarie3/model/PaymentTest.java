package se.kth.iv1350.seminarie3.model;

import junit.framework.TestCase;

/**
* Tests for the Payment class using JUnit 3.
*/
public class PaymentTest extends TestCase {

    public void testPaidAmountIsStoredCorrectly() {
        double paid = 200.0;
        double price = 150.0;
        Payment payment = new Payment(paid, price);

        assertEquals("Paid amount should match the input.", paid, payment.getPaidAmount(), 0.0001);
    }

    public void testChangeIsCalculatedCorrectly() {
        double paid = 200.0;
        double price = 150.0;
        Payment payment = new Payment(paid, price);
        double expectedChange = 50.0;

        assertEquals("Change should be correctly calculated.", expectedChange, payment.getChangeBack(), 0.0001);
    }

    public void testNoChangeWhenExactAmountPaid() {
        double paid = 100.0;
        double price = 100.0;
        Payment payment = new Payment(paid, price);

        assertEquals("Change should be zero when exact amount is paid.", 0.0, payment.getChangeBack(), 0.0001);
    }

    public void testNegativeChangeWhenUnderpaid() {
        double paid = 80.0;
        double price = 100.0;
        Payment payment = new Payment(paid, price);
        double expectedNegativeChange = -20.0;

        assertEquals("Change should be negative when underpaid.", expectedNegativeChange, payment.getChangeBack(), 0.0001);
    }
}

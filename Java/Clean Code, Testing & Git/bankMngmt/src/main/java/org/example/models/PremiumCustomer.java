package org.example.models;

/**
 * A premium bank customer with enhanced benefits, a higher minimum balance
 * requirement, and waived account fees.
 *
 * @see RegularCustomer
 */
public class PremiumCustomer extends Customer {

    private double minimumBalance = 10000;

    /**
     * Creates a premium customer.
     *
     * @param name    the customer's full name
     * @param contact the customer's contact information
     * @param address the customer's mailing address
     * @param age     the customer's age in years
     */
    public PremiumCustomer(String name, String contact, String address, int age) {
        super(name, contact, address, age);
    }

    /**
     * @return always {@code true} — premium customers have fees waived
     */
    public boolean hasWaivedFees() {
        return true;
    }

    /**
     * @return a multi-line block of customer details, including the premium
     *         minimum-balance requirement and fee waiver status
     */
    @Override
    public String displayCustomerDetails() {
        return String.format("""
            Customer ID:     %s
            Name:            %s
            Age:             %d
            Contact:         %s
            Address:         %s
            Type:            %s
            Minimum Balance: $%,.2f
            Fee Waiver:      Yes""",
                getCustomerId(),
                getName(),
                getAge(),
                getContact(),
                getAddress(),
                getCustomerType(),
                minimumBalance);
    }

    /** @return {@code "Premium"} */
    @Override
    public String getCustomerType() {
        return "Premium";
    }

    /** @return the minimum balance required for premium status */
    public double getMinimumBalance() {
        return minimumBalance;
    }

    /** @param minimumBalance the new minimum balance requirement */
    public void setMinimumBalance(double minimumBalance) {
        this.minimumBalance = minimumBalance;
    }
}
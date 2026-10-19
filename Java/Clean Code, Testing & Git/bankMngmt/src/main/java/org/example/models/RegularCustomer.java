package org.example.models;

/**
 * A regular bank customer with standard services and no fee waivers.
 *
 * @see PremiumCustomer
 */
public class RegularCustomer extends Customer {

    /**
     * Creates a regular customer.
     *
     * @param name    the customer's full name
     * @param contact the customer's contact information
     * @param address the customer's mailing address
     * @param age     the customer's age in years
     */
    public RegularCustomer(String name, String contact, String address, int age) {
        super(name, contact, address, age);
    }

    /**
     * @return a multi-line block of customer details
     */
    @Override
    public String displayCustomerDetails() {
        return String.format("""
            Customer ID: %s
            Name:        %s
            Age:         %d
            Contact:     %s
            Address:     %s
            Type:        %s""",
                getCustomerId(),
                getName(),
                getAge(),
                getContact(),
                getAddress(),
                getCustomerType());
    }

    /** @return {@code "Regular"} */
    @Override
    public String getCustomerType() {
        return "Regular";
    }
}
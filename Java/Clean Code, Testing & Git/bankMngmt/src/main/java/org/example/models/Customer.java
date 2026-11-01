package org.example.models;

/**
 * Abstract base class for bank customers.
 *
 * <p>Every customer has an auto-generated ID of the form {@code CUS001}
 * along with name, contact, address, and age. Concrete subclasses
 * ({@link RegularCustomer}, {@link PremiumCustomer}) provide their own
 * type label and formatted detail block.</p>
 */
public abstract class Customer {

    private String customerId, name, contact, address;
    private int age;

    /** Static counter used to generate sequential customer IDs. */
    static int customerCounter = 0;

    /**
     * Creates a new customer and assigns a unique customer ID.
     *
     * @param name    the customer's full name
     * @param contact the customer's contact information
     * @param address the customer's mailing address
     * @param age     the customer's age in years
     */
    public Customer(String name, String contact, String address, int age) {
        this.customerId = String.format("CUS%03d", ++customerCounter);
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.age = age;
    }

    /** Resets the static counter used for customer-ID generation. */
    public static void resetCounter() {
        customerCounter = 0;
    }

    /**
     * Returns a multi-line block of customer details suitable for printing
     * to the console.
     *
     * @return a formatted string of customer information
     */
    public abstract String displayCustomerDetails();

    /**
     * Returns the type label of the customer (e.g., {@code "Regular"},
     * {@code "Premium"}).
     *
     * @return the customer type
     */
    public abstract String getCustomerType();

    /** @return the auto-generated customer ID (e.g., {@code "CUS001"}) */
    public String getCustomerId() {
        return customerId;
    }

    /** @param customerId the new customer ID */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /** @return the customer's name */
    public String getName() {
        return name;
    }

    /** @param name the new customer name */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the customer's contact information */
    public String getContact() {
        return contact;
    }

    /** @param contact the new contact information */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /** @return the customer's address */
    public String getAddress() {
        return address;
    }

    /** @param address the new address */
    public void setAddress(String address) {
        this.address = address;
    }

    /** @return the customer's age */
    public int getAge() {
        return age;
    }

    /** @param age the new age */
    public void setAge(int age) {
        this.age = age;
    }

    /** @return the current value of the static customer counter */
    public static int getCustomerCounter() {
        return customerCounter;
    }

    /** @param customerCounter the new value for the static customer counter */
    public static void setCustomerCounter(int customerCounter) {
        Customer.customerCounter = customerCounter;
    }
}
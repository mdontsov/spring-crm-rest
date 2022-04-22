package demo.rest.service;

import demo.rest.entity.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getCustomers();

    void addCustomer(Customer customer);

    Customer getCustomer(int customerId);

    void deleteCustomer(int customerId);

    Customer updateCustomer(Customer customer);
}

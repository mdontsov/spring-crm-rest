package demo.rest.controller;

import demo.rest.entity.Customer;
import demo.rest.service.CustomerService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RESTController {

    @Autowired // <- DI
    private CustomerService service;

    @GetMapping("/customers")
    public List<Customer> getCustomers() {
        return service.getCustomers();
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomer(@PathVariable int id) throws ObjectNotFoundException {
        Customer customer = service.getCustomer(id);
        if (customer == null) {
            throw new ObjectNotFoundException("Given customer ID not found: " + id);
        }

        return service.getCustomer(id);
    }

    @PostMapping("/customers")
    public Customer addCustomer(@RequestBody @NotNull Customer customer) { // <- binds POJO as method parameter
        customer.setId(0);
        service.addCustomer(customer);
        return customer;
    }

    @DeleteMapping("/customers/{id}")
    public String deleteCustomer(@PathVariable int id) throws ObjectNotFoundException {
        Customer customer = service.getCustomer(id);
        service.deleteCustomer(customer.getId());
        return "Deleted customer ID: " + customer.getId();
    }

    @PutMapping("/customers")
    public Customer updateCustomer(@RequestBody Customer customer) {
        return service.updateCustomer(customer);
    }
}

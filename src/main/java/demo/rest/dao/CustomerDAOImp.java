package demo.rest.dao;

import demo.rest.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDAOImp implements CustomerDAO { // <- interface is a Proxy object that Spring uses for DI

    @Autowired
    private SessionFactory factory;

    @Override
    public List<Customer> getCustomers() {
        Session session = factory.getCurrentSession();

        // create query
        Query<Customer> query = session.createQuery("from Customer order by lastName asc ");

        // use query and return the results
        return query.getResultList();
    }

    @Override
    public void addCustomer(@NotNull Customer customer) {
        Session session = factory.getCurrentSession();
        if (customer.getFirstName().equals("")
                && customer.getLastName().equals("")
                && customer.getEmail().equals("")) {
            session.getTransaction().rollback();
        } else {
            session.saveOrUpdate(customer); // <- IF primary key empty then SAVE otherwise UPDATE
        }
    }

    @Override
    public Customer getCustomer(int customerId) {
        Session session = factory.getCurrentSession();
        Customer customer = session.get(Customer.class, customerId);
        return customer;
    }

    @Override
    public void deleteCustomer(int customerId) {
        Session session = factory.getCurrentSession();
        Customer customer = session.get(Customer.class, customerId);
        session.delete(customer);
    }
}

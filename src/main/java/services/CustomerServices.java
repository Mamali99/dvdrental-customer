package services;

import entities.AddressHref;
import entities.Customer;
import entities.CustomerDTO;
import entities.StoreHref;
import jakarta.ejb.Stateless;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
@Stateless
@Named
public class CustomerServices {

    @PersistenceContext
    private EntityManager entityManager;

    public List<CustomerDTO> getFirst20Customers() {
        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c", Customer.class);
        query.setMaxResults(20);

        List<Customer> customers = query.getResultList();
        List<CustomerDTO> customerDTOs = new ArrayList<>();

        for (Customer customer : customers) {
            CustomerDTO dto = new CustomerDTO();

            dto.setId(customer.getCustomer_id());
            dto.setActive(customer.getActive());
            dto.setActivebool(customer.getActivebool());
            dto.setCreateDate(customer.getCreate_date());
            dto.setEmail(customer.getEmail());
            dto.setFirstName(customer.getFirst_name());
            dto.setLastName(customer.getLast_name());

            // Assuming you have a way to fetch the href for a store and address
            // For this example, I'm just placing a placeholder value
            StoreHref storeHref = new StoreHref();
            storeHref.setHref("path_to_store/" + customer.getStore_id());
            dto.setStore(storeHref);

            AddressHref addressHref = new AddressHref();
            // Assuming Address has a method like getId()
            addressHref.setHref("path_to_address/" + customer.getAddress().getAddress_id());
            dto.setAddress(addressHref);

            customerDTOs.add(dto);
        }

        return customerDTOs;
    }
}

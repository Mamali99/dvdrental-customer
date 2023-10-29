package services;

import entities.*;
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

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();

        // Set basic properties
        customer.setActive(customerDTO.getActive());
        customer.setActivebool(customerDTO.getActivebool());
        customer.setCreate_date(customerDTO.getCreateDate());
        customer.setEmail(customerDTO.getEmail());
        customer.setFirst_name(customerDTO.getFirstName());
        customer.setLast_name(customerDTO.getLastName());

        // Assuming hrefs are something like /store/1 and /address/5
        // Extracting IDs from hrefs
        String storeHref = customerDTO.getStore().getHref();
        String[] storeHrefParts = storeHref.split("/");
        if (storeHrefParts.length > 0) {
            customer.setStore_id(Integer.parseInt(storeHrefParts[storeHrefParts.length - 1]));
        }

        // Do similar for address. We'll assume Address has an ID field.
        String addressHref = customerDTO.getAddress().getHref();
        String[] addressHrefParts = addressHref.split("/");
        if (addressHrefParts.length > 0) {
            Integer addressId = Integer.parseInt(addressHrefParts[addressHrefParts.length - 1]);
            Address address = entityManager.find(Address.class, addressId);
            customer.setAddress(address);
        }
        // Persist customer entity
        entityManager.persist(customer);

        // Convert persisted Customer to CustomerDTO
        CustomerDTO persistedCustomerDTO = new CustomerDTO();
        persistedCustomerDTO.setId(customer.getCustomer_id());
        persistedCustomerDTO.setActive(customer.getActive());
        persistedCustomerDTO.setActivebool(customer.getActivebool());
        persistedCustomerDTO.setCreateDate(customer.getCreate_date());
        persistedCustomerDTO.setEmail(customer.getEmail());
        persistedCustomerDTO.setFirstName(customer.getFirst_name());
        persistedCustomerDTO.setLastName(customer.getLast_name());
        persistedCustomerDTO.setStore(new StoreHref("path_to_store/" + customer.getStore_id()));
        persistedCustomerDTO.setAddress(new AddressHref("path_to_address/" + customer.getAddress().getAddress_id()));

        return persistedCustomerDTO;


    }
}

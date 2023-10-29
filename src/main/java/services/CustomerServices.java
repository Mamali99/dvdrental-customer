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

    public EntityManager getEntityManager(){
        return entityManager;
    }

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

    public Integer getCount() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(c) FROM Customer c", Long.class);
        return query.getSingleResult().intValue();
    }

    public CustomerDTO getCustomerById(int id) {
        // Finde den Kunden in der Datenbank über die ID
        Customer customer = entityManager.find(Customer.class, id);

        // Wenn kein Kunde gefunden wurde, gib null zurück
        if (customer == null) {
            return null;
        }

        // Wandele das Customer-Entity in ein CustomerDTO um
        CustomerDTO dto = new CustomerDTO();

        dto.setId(customer.getCustomer_id());
        dto.setActive(customer.getActive());
        dto.setActivebool(customer.getActivebool());
        dto.setCreateDate(customer.getCreate_date());
        dto.setEmail(customer.getEmail());
        dto.setFirstName(customer.getFirst_name());
        dto.setLastName(customer.getLast_name());

        // Setze die HREFs für den Store und die Adresse
        StoreHref storeHref = new StoreHref();
        storeHref.setHref("path_to_store/" + customer.getStore_id());
        dto.setStore(storeHref);

        AddressHref addressHref = new AddressHref();
        addressHref.setHref("path_to_address/" + customer.getAddress().getAddress_id());
        dto.setAddress(addressHref);

        return dto;
    }

    public List<PaymentDTO> getPaymentsByCustomerId(int id) {
        TypedQuery<Payment> query = entityManager.createQuery("SELECT p FROM Payment p WHERE p.customer.customer_id = :customerId", Payment.class);
        query.setParameter("customerId", id);
        List<Payment> payments = query.getResultList();

        List<PaymentDTO> paymentDTOs = new ArrayList<>();
        for (Payment payment : payments) {
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setId(payment.getPaymentId());
            paymentDTO.setAmount(payment.getAmount().doubleValue());

            // Setzen Sie die Hrefs basierend auf Ihren Endpunkt-URLs
            paymentDTO.setCustomer(new CustomerHref("path_to_customer/" + payment.getCustomer().getCustomer_id()));
            paymentDTO.setStaff(new StaffHref("path_to_staff/" + payment.getStaffId()));
            paymentDTO.setRental(new RentalHref("path_to_rental/" + payment.getRentalId()));

            paymentDTOs.add(paymentDTO);
        }

        return paymentDTOs;
    }
}

package services;

import dto.CustomerDTO;
import dto.PaymentDTO;
import entities.*;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import utils.*;

import java.util.ArrayList;
import java.util.List;
@Stateless
@Named
public class CustomerServices {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private StoreServiceClient storeServiceClient;

    public EntityManager getEntityManager(){
        return entityManager;
    }

    public List<CustomerDTO> getCustomersByPage(int page) {
        int pageSize = 20;

        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c", Customer.class);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);

        List<Customer> customers = query.getResultList();

        if(customers == null){
            return null;
        }
        List<CustomerDTO> customerDTOs = new ArrayList<>();

        for (Customer customer : customers) {
            customerDTOs.add(convertToDTO(customer));
        }

        return customerDTOs;
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = convertFromDTO(customerDTO);
        entityManager.persist(customer);
        return convertToDTO(customer);
    }

    private Integer extractIdFromHref(String href) {
        String[] parts = href.split("/");
        return Integer.parseInt(parts[parts.length - 1]);
    }

    public Integer getCount() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(c) FROM Customer c", Long.class);
        return query.getSingleResult().intValue();
    }

    public CustomerDTO getCustomerById(int id) {
        Customer customer = entityManager.find(Customer.class, id);
        if (customer == null) {
            return null;
        }
        return convertToDTO(customer);
    }

    public boolean doesCustomerExist(int id) {
        Customer customer = entityManager.find(Customer.class, id);
        return customer != null;
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
            paymentDTO.setCustomer(new CustomerHref("http://localhost:8083/customers/" + payment.getCustomer().getCustomer_id()));
            paymentDTO.setStaff(new StaffHref("http://localhost:8082/staff/" + payment.getStaffId()));
            paymentDTO.setRental(new RentalHref("http://localhost:8082/rentals/" + payment.getRentalId()));
            paymentDTOs.add(paymentDTO);
        }

        return paymentDTOs;
    }

    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getCustomer_id());
        dto.setActive(customer.getActive());
        dto.setActivebool(customer.getActivebool());
        dto.setCreateDate(customer.getCreate_date());
        dto.setEmail(customer.getEmail());
        dto.setFirstName(customer.getFirst_name());
        dto.setLastName(customer.getLast_name());

        StoreHref storeHref = new StoreHref();
        storeHref.setHref("http://localhost:8082/stores/" + customer.getStore_id());
        dto.setStore(storeHref);

        AddressHref addressHref = new AddressHref();
        addressHref.setHref("http://localhost:8083/addresses/" + customer.getAddress().getAddress_id());
        dto.setAddress(addressHref);

        return dto;
    }


    private Customer convertFromDTO(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setActive(dto.getActive());
        customer.setActivebool(dto.getActivebool());
        customer.setCreate_date(dto.getCreateDate());
        customer.setEmail(dto.getEmail());
        customer.setFirst_name(dto.getFirstName());
        customer.setLast_name(dto.getLastName());

        // Extrahieren und überprüfen der Store ID
        Integer storeId = extractIdFromHref(dto.getStore().getHref());
        if (!storeServiceClient.checkStoreExists(storeId)) {
            throw new WebApplicationException("Store nicht gefunden.", Response.Status.BAD_REQUEST);
        }
        customer.setStore_id(storeId);

        // Extrahieren und überprüfen der Address ID
        Integer addressId = extractIdFromHref(dto.getAddress().getHref());
        Address address = entityManager.find(Address.class, addressId);
        if (address == null) {
            throw new WebApplicationException("Adresse nicht gefunden.", Response.Status.BAD_REQUEST);
        }
        customer.setAddress(address);

        return customer;
    }

}

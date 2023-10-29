package services;

import entities.Address;
import entities.AddressDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class AddressServices {

    @PersistenceContext
    private EntityManager entityManager;

    public List<AddressDTO> getFirst100Addresses() {
        TypedQuery<Address> query = entityManager.createQuery("SELECT a FROM Address a", Address.class);
        query.setMaxResults(100);
        List<Address> addresses = query.getResultList();

        List<AddressDTO> addressDTOs = new ArrayList<>();
        for (Address address : addresses) {
            AddressDTO dto = new AddressDTO();
            dto.setId(address.getAddress_id());
            dto.setAddress(address.getAddress());
            dto.setAddress2(address.getAddress2());
            dto.setDistrict(address.getDistrict());
            dto.setPhone(address.getPhone());
            dto.setPostalCode(address.getPostal_code());

            // Set city and country from related entities
            if(address.getCity() != null) {
                dto.setCity(address.getCity().getCity());
                if(address.getCity().getCountry() != null) {
                    dto.setCountry(address.getCity().getCountry().getCountry());
                }
            }

            addressDTOs.add(dto);
        }

        return addressDTOs;
    }
}

package services;

import entities.Address;
import entities.AddressDTO;
import entities.City;
import entities.Country;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

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

    @Transactional
    public AddressDTO createAddress(AddressDTO addressDTO) {
        Address address = new Address();
        address.setAddress_id(addressDTO.getId());
        address.setAddress(addressDTO.getAddress());
        address.setAddress2(addressDTO.getAddress2());
        address.setDistrict(addressDTO.getDistrict());
        address.setPhone(addressDTO.getPhone());
        address.setPostal_code(addressDTO.getPostalCode());


        City city = findCity(addressDTO.getCity(), addressDTO.getCountry());
        if(city == null) {
            throw new IllegalStateException("City does not exist");
        }
        address.setCity(city);

        entityManager.persist(address); // Persistiere die Address-Entity

        return addressDTO;
    }

    private AddressDTO convertToAddressDTO(Address address) {
        AddressDTO newAddressDTO = new AddressDTO();
        newAddressDTO.setId(address.getAddress_id());
        newAddressDTO.setAddress(address.getAddress());
        newAddressDTO.setAddress2(address.getAddress2());
        newAddressDTO.setDistrict(address.getDistrict());
        newAddressDTO.setPhone(address.getPhone());
        newAddressDTO.setPostalCode(address.getPostal_code());
        if(address.getCity() != null) {
            newAddressDTO.setCity(address.getCity().getCity());
            if(address.getCity().getCountry() != null) {
                newAddressDTO.setCountry(address.getCity().getCountry().getCountry());
            }
        }
        return newAddressDTO;
    }

    private City findCity(String cityName, String countryName) {
        TypedQuery<City> cityQuery = entityManager.createQuery("SELECT c FROM City c WHERE c.city = :cityName AND c.country.country = :countryName", City.class);
        cityQuery.setParameter("cityName", cityName);
        cityQuery.setParameter("countryName", countryName);
        City city;
        try {
            city = cityQuery.getSingleResult();
        } catch (NoResultException e) {
            throw new IllegalStateException("City or Country does not exist");
        }
        return city;
    }




}

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


    public City findCity(String cityName, String countryName) {
        // Suche zuerst das Land
        TypedQuery<Country> countryQuery = entityManager.createQuery(
                "SELECT c FROM Country c WHERE c.country = :countryName", Country.class);
        countryQuery.setParameter("countryName", countryName);

        Country foundCountry = countryQuery.getSingleResult();
        if (foundCountry == null) {
            return null;  // Land wurde nicht gefunden
        }

        // Suche dann die Stadt, die diesem Land zugeordnet ist
        TypedQuery<City> cityQuery = entityManager.createQuery(
                "SELECT ci FROM City ci WHERE ci.city = :cityName AND ci.country = :foundCountry", City.class);
        cityQuery.setParameter("cityName", cityName);
        cityQuery.setParameter("foundCountry", foundCountry);

        City foundCity = cityQuery.getSingleResult();
        if (foundCity == null) {
            return null;  // Stadt wurde nicht gefunden oder gehört nicht zu diesem Land
        }

        // Überprüfe, ob die Stadt das erwartete Land als Land hat
        if (foundCity.getCountry().getCountry_id().equals(foundCountry.getCountry_id())) {
            return foundCity;  // Stadt wurde gefunden und gehört zu dem erwarteten Land
        } else {
            return null;  // Stadt gehört nicht zu dem erwarteten Land
        }
    }




}

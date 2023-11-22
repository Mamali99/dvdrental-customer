package entities;


import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@Entity
@Table(name = "address")
public class Address {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_address_id_seq")
        @SequenceGenerator(name = "address_address_id_seq", sequenceName = "public.address_address_id_seq", allocationSize = 1)
        private Integer address_id;

        @Column(nullable = false, length = 50)
        private String address;

        @Column(length = 50)
        private String address2;

        @Column(nullable = false, length = 20)
        private String district;


        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "city_id", nullable = false)
        private City city;

        @Column(length = 10)
        private String postal_code;

        @Column(nullable = false, length = 20)
        private String phone;

        @Column(name = "last_update", nullable = false)
        private Timestamp last_update = Timestamp.valueOf(LocalDateTime.now());

        public Integer getAddress_id() {
                return address_id;
        }

        public void setAddress_id(Integer address_id) {
                this.address_id = address_id;
        }

        public String getAddress() {
                return address;
        }

        public void setAddress(String address) {
                this.address = address;
        }

        public String getAddress2() {
                return address2;
        }

        public void setAddress2(String address2) {
                this.address2 = address2;
        }

        public String getDistrict() {
                return district;
        }

        public void setDistrict(String district) {
                this.district = district;
        }

        public City getCity() {
                return city;
        }

        public void setCity(City city) {
                this.city = city;
        }

        public String getPostal_code() {
                return postal_code;
        }

        public void setPostal_code(String postal_code) {
                this.postal_code = postal_code;
        }

        public String getPhone() {
                return phone;
        }

        public void setPhone(String phone) {
                this.phone = phone;
        }

        public Timestamp getLast_update() {
                return last_update;
        }

        public void setLast_update(Timestamp last_update) {
                this.last_update = last_update;
        }
}

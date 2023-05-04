package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

public class Address {
    String street;
    String zipcode;
    String house_number;

    public Address(String street, String house_number, String zipcode){
        this.street = street;
        this.zipcode = zipcode;
        this.house_number = house_number;
    }

    @Override
    public String toString() {
        return street + ' ' + house_number + ", " + zipcode;
    }
}

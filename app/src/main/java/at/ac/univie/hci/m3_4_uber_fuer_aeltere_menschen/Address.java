package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import java.util.Objects;

public class Address {
    String addressLine1;
    String addressLine2;

    public Address(String addressLine1, String addressLine2){
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
    }
    public Address(){

    }
    public void setAddressLine1(String address) {
        this.addressLine1 = address;
    }
    public void setAddressLine2(String address) {
        this.addressLine2 = address;
    }
    public String getAddressLine1() {
        return addressLine1;
    }
    public String getAddressLine2() {
        return addressLine2;
    }

    public String toString() {
        return addressLine2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(addressLine2, address.addressLine2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressLine2);
    }
}

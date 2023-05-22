package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import java.util.ArrayList;
import java.util.Objects;

public class User {
    String userName;
    String password;
    String email;
    boolean online;
    int age;
    String occupation;
    String description;
    Escort escortRequest = new Escort();
    ArrayList<Escort> escorts = new ArrayList<>();
    ArrayList<Escort> finishedEscorts = new ArrayList<>();
    ArrayList<Address> addressHistory = new ArrayList<>();
    ArrayList<Address> addressFavorites = new ArrayList<>();

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        description = "";
    }

    public String getUserName() {
        return userName;
    }
    public String getEmail() {return email;}

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public String getOccupation() {
        return occupation;
    }
    public String getDescription() {
        return description;
    }
    public boolean getOnline(){
        return online;
    }
    public ArrayList<Escort> getEscorts() {
        return escorts;
    }
    public Escort getEscortRequest(){
        return escortRequest;
    }
    public ArrayList<Escort> getFinishedEscorts(){
        return finishedEscorts;
    }
    public ArrayList<Address> getAddressHistory(){
        return addressHistory;
    }
    public ArrayList<Address> getAddressFavorites(){
        return addressFavorites;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }
}
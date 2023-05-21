package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import java.util.ArrayList;
import java.util.Objects;

public class User {
    String userName;
    String password;
    boolean online;
    int age;
    String occupation;
    String description;
    Escort escortRequest = new Escort();
    ArrayList<Escort> escorts = new ArrayList<>();
    ArrayList<Escort> finishedEscorts = new ArrayList<>();
    ArrayList<Address> addressHistory = new ArrayList<>();
    ArrayList<Address> addressFavorites = new ArrayList<>();

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

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
        return Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }
}

    /*
    public static String username = "Maria";
    public static String password = "password";
    public static boolean online = false;
    public static int age = 79;
    public static String occupation = "Pensionistin";
    Escort active_escort ;
    public static String description = "Hallo, ich bin Maria.\n" +
            "Ich habe Probleme mit dem Fuß, deshalb bin ich oft etwas langsam. " +
            "Ich höre nicht mehr so gut, aber habe noch immer eine gute Orientierung.";

    public static Escort escort_request = new Escort();
    public static ArrayList<Escort> escorts = new ArrayList<>();
    public static ArrayList<Escort> finished = new ArrayList<>();
    public static ArrayList<Address> address_history = new ArrayList<>();
    public static ArrayList<Address> address_favorites = new ArrayList<>();
    static {
        address_favorites.add(new Address("Zu Hause", "Teststraße 98, 1100 Wien, Österreich"));
        address_favorites.add(new Address("Krankenhaus", "Favoritenstraße 4, 1170 Wien, Österreich"));
    }
}
*/
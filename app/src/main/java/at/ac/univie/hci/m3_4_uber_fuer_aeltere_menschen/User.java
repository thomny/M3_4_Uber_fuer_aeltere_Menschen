package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import java.util.ArrayList;

public class User {
    public static String username = "Maria";
    public static String password = "password";
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

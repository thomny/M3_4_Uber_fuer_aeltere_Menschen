package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import java.util.ArrayList;

public class User {
    String username;
    String password;
    //ArrayList<Escort> escorts;
    Escort active_escort;
    public static Escort escort_request = new Escort();
    public static ArrayList<Escort> escorts = new ArrayList<>();
}

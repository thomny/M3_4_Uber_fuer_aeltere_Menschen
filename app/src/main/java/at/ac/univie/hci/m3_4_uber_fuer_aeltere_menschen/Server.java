package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import java.util.ArrayList;

public class Server {
    public static ArrayList<Accompaniment> accompaniments = new ArrayList<>();
    static {
        Server.accompaniments.add(
                new Accompaniment("Anna",20,"Student",
                        "Hi, nice to meet you. My name is Anna and I'm 20 years old"));
        Server.accompaniments.add(
                new Accompaniment("Peter",23,"Student",
                        "Hi, nice to meet you. My name is Peter and I'm 23 years old"));
        Server.accompaniments.add(
                new Accompaniment("Maximilian",27,"Student",
                        "Hi, nice to meet you. My name is Max and I'm 27 years old"));
        Server.accompaniments.add(
                new Accompaniment("Julia",30,"Student",
                        "Hi, nice to meet you. My name is Julia and I'm 30 years old"));
    }
}
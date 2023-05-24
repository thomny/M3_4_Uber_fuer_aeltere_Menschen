package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

public class Server { //Simulation eines Servers (Mock)
    public static ArrayList<Accompaniment> accompaniments = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();
    public static TreeMap userList = new TreeMap();
    public static User user;
    static {
        Server.accompaniments.add(
                new Accompaniment("Anna",20,"Student",
                        "Hallo, mein Name ist Anna. Ich spreche deutsch, türkisch und ein wenig serbisch.\n" +
                                "Ich habe viel Geduld und begleite dich gerne zu Arztterminen."));
        Server.accompaniments.add(
                new Accompaniment("Peter",23,"Student",
                        "Hi, nice to meet you. My name is Peter and I'm 23 years old"));
        Server.accompaniments.add(
                new Accompaniment("Maximilian",27,"Student",
                        "Hi, nice to meet you. My name is Max and I'm 27 years old"));
        Server.accompaniments.add(
                new Accompaniment("Julia",30,"Student",
                        "Hi, nice to meet you. My name is Julia and I'm 30 years old"));
        Server.userList.put("dummy@mail.at",new User("Dummy","dummy@mail.at","dummy"));

    }
    public static void accept(Escort escort){
        AsyncTask<Void, Void, Void> acceptTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Log.d("TAG","executed");
                escort.setStatus(EscortStatus.ACCEPTED);
                Log.d("TAG",escort.getStatus().toString());
            }
        };
        acceptTask.execute();
    }
}
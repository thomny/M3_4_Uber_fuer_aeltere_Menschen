package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class RequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        //Zugriff auf CLOSE-Icon für alle Fragmente in Request
        ImageButton closeButton = findViewById(R.id.closeButton);
        //RequestActivity ist aufgeteilt in Fragments, beginnend mit RequestStartFragment
        RequestStartFragment requestStartFragment = new RequestStartFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,requestStartFragment).commit();
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override //auf CLOSE-Icon klicken führt zu MainActivity zurück
            public void onClick(View view) {
                close();
            }
        });
    }

    public void close() { //Zurueck-Icon fuehrt zur MainActivity zurueck
        //ausgewählte Daten werden bei Abbruch der Anforderung gelöscht
        Server.user.getEscortRequest().setTime(false);
        Server.user.getEscortRequest().setStart(null);
        Server.user.getEscortRequest().setDestination(null);
        Server.user.getEscortRequest().setService(null);
        Server.user.getEscortRequest().setAccompaniment(null);
        finish();
    }
}
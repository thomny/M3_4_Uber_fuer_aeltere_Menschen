package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
            @Override //auf CLOSE-Icon klicken öffnet Abbruch-Dialogfenster
            public void onClick(View view) {
                openCancelDialog();
            }
        });
    }

    public void openCancelDialog(){
        //Dialogfenster: Fahrtanforderungs-Abbruch
        Dialog cancelDialog = new Dialog(this);
        cancelDialog.setContentView(R.layout.cancel_dialogue_layout);
        cancelDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button yesButton = cancelDialog.findViewById(R.id.yesButton);
        Button noButton = cancelDialog.findViewById(R.id.noButton);
        TextView dialogText = cancelDialog.findViewById(R.id.textView);
        dialogText.setText("Wollen Sie wirklich abbrechen?");
        yesButton.setText("Ja, abbrechen");
        noButton.setText("Nein, nicht abbrechen");
        ImageView closeButton = cancelDialog.findViewById(R.id.closeButton);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelDialog.dismiss();
                close();
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelDialog.dismiss();
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelDialog.dismiss();
            }
        });
        cancelDialog.show();
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
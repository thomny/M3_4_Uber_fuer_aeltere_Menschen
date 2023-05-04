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
    RequestStartFragment requestStartFragment = new RequestStartFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        //RequestActivity ist aufgeteilt in Fragments
        getSupportFragmentManager().beginTransaction().replace(R.id.container,requestStartFragment).commit();
        ImageButton closeButton = findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
            }
        });
    }

    public void close() { //Zurueck-Icon fuehrt zur vorherigen Activity zurueck
        Intent back = new Intent(this, MainActivity.class);
        startActivity(back);
    }
}
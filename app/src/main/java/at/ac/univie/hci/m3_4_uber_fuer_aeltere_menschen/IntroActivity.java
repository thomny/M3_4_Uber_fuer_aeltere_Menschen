package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import androidx.appcompat.app.AppCompatActivity;

import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class IntroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        IntroWelcome introWelcomeFragment = new IntroWelcome();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,introWelcomeFragment).commit();

    }
}
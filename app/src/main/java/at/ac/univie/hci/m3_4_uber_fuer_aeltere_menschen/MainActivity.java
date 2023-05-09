package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navigationView;
    HomeFragment homeFragment = new HomeFragment();
    AccountFragment accountFragment = new AccountFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.navigationbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
        //Klicken auf ein Icon aendert das Fragment
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home: getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                        return true;
                    case R.id.account: getSupportFragmentManager().beginTransaction().replace(R.id.container,accountFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}
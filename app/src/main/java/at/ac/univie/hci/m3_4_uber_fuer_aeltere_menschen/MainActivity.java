package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

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
        //Mock
        User user;
        user = (User) Server.userList.get("dummy@mail.at");
        user.setProfilepicture(getResources().getDrawable(R.drawable.profilepicture0));
        user.setAge(99);
        user.setOccupation("Testuser");
        user.setDescription("Bei diesem Benutzer handelt es sich um einen Dummy-User.");
        Server.accompaniments.get(0).setProfilepicture(getResources().getDrawable(R.drawable.profilepicture3));
        Server.accompaniments.get(1).setProfilepicture(getResources().getDrawable(R.drawable.profilepicture1));
        Server.accompaniments.get(2).setProfilepicture(getResources().getDrawable(R.drawable.profilepicture2));
        Server.accompaniments.get(3).setProfilepicture(getResources().getDrawable(R.drawable.profilepicture4));
        //Wenn kein User angemeldet ist, wird zur LoginActivity weitergeleitet
        if(Server.user == null){
            Intent login = new Intent(this, IntroActivity.class);
            startActivity(login);
        } else {
            //Bottom-Navigationsleiste und Framelayout-Logik
            navigationView = findViewById(R.id.navigationbar);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
            navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.home:
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                            return true;
                        case R.id.account:
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, accountFragment).commit();
                            return true;
                    }
                    return false;
                }
            });
        }
    }
}
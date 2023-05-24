package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class EscortInfoActivity extends AppCompatActivity {
    Escort escort;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escort_info);
        ImageButton closeButton = findViewById(R.id.closeButton);
        //Position des Begleitungs-Items wird aus dem Intent genommen
        Integer pos;
        pos = getIntent().getIntExtra("position",0);
        escort = Server.user.getEscorts().get(pos);
        //Position des Begleitungs-Items wird in eine Bundle für das nächste Fragment gegeben
        Bundle bundle = new Bundle();
        bundle.putInt("position",pos);
        //EscortInfoActivity ist aufgeteilt in Fragments, beginnend mit EscortSummaryFragment
        EscortSummaryFragment escortSummaryFragment = new EscortSummaryFragment();
        escortSummaryFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,escortSummaryFragment).commit();
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
            }
        });
    }

    public void close() {
        finish();
    }
}
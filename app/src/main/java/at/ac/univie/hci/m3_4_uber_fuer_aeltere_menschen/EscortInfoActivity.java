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
        //Activity ist aufgeteilt in Fragments
        //escort = (Escort) getIntent().getSerializableExtra("escort");
        //Integer pos = getIntent().getIntExtra("",0);
        Integer pos;
        pos = getIntent().getIntExtra("position",0);
        escort = User.escorts.get(pos);
        Bundle bundle = new Bundle();
        bundle.putInt("position",pos);
        EscortSummaryFragment escortSummaryFragment = new EscortSummaryFragment();
        escortSummaryFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,escortSummaryFragment).commit();
        ImageButton closeButton = findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
            }
        });
    }

    public void close() {
        Intent back = new Intent(this, MainActivity.class);
        startActivity(back);
    }
}
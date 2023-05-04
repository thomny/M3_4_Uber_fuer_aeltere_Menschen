package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_home, container, false);
        //Zugriff auf die Komponenten in contentView
        Button requestButton = contentView.findViewById(R.id.requestButton);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override //der SEARCH-Button wird mit der Suchfunktion onQueryTextSubmit verbunden
            public void onClick(View view) {
                startRequest();
            }
        });
        return contentView;
    }

    public void startRequest () {
        //Intent fuer das Wechseln der momentanen Activity zu ArtistInfoActivity wird erstellt
        Intent request = new Intent(getContext(), RequestActivity.class);
        startActivity(request); //Ausfuehren des Intents
    }
}
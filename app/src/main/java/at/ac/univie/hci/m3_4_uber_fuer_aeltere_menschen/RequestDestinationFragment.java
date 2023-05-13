package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;

public class RequestDestinationFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_request_destination, container, false);
        ImageButton backButton = contentView.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        Button nextButton = contentView.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });
        SearchView searchbar = contentView.findViewById(R.id.searchbar);
        return contentView;
    }

    public void back() { //Zurueck-Icon fuehrt zur vorherigen Activity zurueck
        RequestStartFragment requestStartFragment = new RequestStartFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,requestStartFragment).commit();
    }
    public void next() { //temporaere Loesung
        Address destination_address = new Address();
        User.escort_request.setDestination(destination_address);
        RequestTimeFragment requestTimeFragment = new RequestTimeFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,requestTimeFragment).commit();
    }
}
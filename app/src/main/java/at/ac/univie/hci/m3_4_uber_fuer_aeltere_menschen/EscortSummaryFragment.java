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

public class EscortSummaryFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_escort_summary, container, false);
        Button nextButton = contentView.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });
        return contentView;
    }

    public void next() { //temporaere Loesung -
        //RequestDestinationFragment requestDestinationFragment = new RequestDestinationFragment();
        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,requestDestinationFragment).commit();
    }
}
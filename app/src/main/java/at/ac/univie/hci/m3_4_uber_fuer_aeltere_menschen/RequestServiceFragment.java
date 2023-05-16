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

public class RequestServiceFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_request_service, container, false);

        ImageButton backButton = contentView.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        Button autoServiceButton = contentView.findViewById(R.id.autoServiceButton);
        autoServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next(Service.AUTO);
            }
        });
        Button oeffentlichServiceButton = contentView.findViewById(R.id.oeffentlichServiceButton);
        oeffentlichServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next(Service.OEFFENTLICH);
            }
        });
        return contentView;
    }

    public void back() { //Zurueck-Icon fuehrt zur vorherigen Activity zurueck
        RequestTimeFragment requestTimeFragment = new RequestTimeFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,requestTimeFragment).commit();
    }
    public void next(Service service) { //temporaere Loesung
        User.escort_request.setService(service);
        RequestAccompanimentListFragment requestAccompanimentListFragment = new RequestAccompanimentListFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,requestAccompanimentListFragment).commit();
    }
}
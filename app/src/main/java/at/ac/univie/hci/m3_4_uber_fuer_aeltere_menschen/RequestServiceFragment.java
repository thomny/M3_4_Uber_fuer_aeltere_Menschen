package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

public class RequestServiceFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_request_service, container, false);
        //Zugriff auf die Komponenten in contentView
        TextView cell1 = contentView.findViewById(R.id.cell1);
        cell1.setBackgroundResource(R.drawable.progress_bar_cell_filled);
        TextView cell2 = contentView.findViewById(R.id.cell2);
        cell2.setBackgroundResource(R.drawable.progress_bar_cell_filled);
        TextView cell3 = contentView.findViewById(R.id.cell3);
        cell3.setBackgroundResource(R.drawable.progress_bar_cell_filled);
        ImageButton backButton = contentView.findViewById(R.id.backButton);
        Button autoServiceButton = contentView.findViewById(R.id.autoServiceButton);
        Button oeffentlichServiceButton = contentView.findViewById(R.id.oeffentlichServiceButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        // Beide Button führen zum nächsten Fragment
        autoServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override // "Auto" wird als gewählter Service gewählt
            public void onClick(View view) {
                next(Service.AUTO);
            }
        });
        oeffentlichServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override // "Öffentliche Verkehrsmittel" wird als gewählter Service gewählt
            public void onClick(View view) {
                next(Service.OEFFENTLICH);
            }
        });
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                back();
            }
        });
        return contentView;
    }

    public void back() { //Zurueck-Icon fuehrt zur MainActivity zurueck
        RequestTimeFragment requestTimeFragment = new RequestTimeFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,requestTimeFragment).commit();
    }
    public void next(Service service) {
        Server.user.getEscortRequest().setService(service); //gewählter Service wird in EscortRequest eingefügt
        RequestAccompanimentListFragment requestAccompanimentListFragment = new RequestAccompanimentListFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,requestAccompanimentListFragment).commit();
    }
}
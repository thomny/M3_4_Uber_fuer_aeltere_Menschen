package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class RequestAccompanimentListFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_request_accompaniment_list, container, false);
        //Zugriff auf die Komponenten in contentView
        ImageButton backButton = contentView.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        ListView accompanimentsList = contentView.findViewById(R.id.accompanimentsList);
        TextView listStatus = contentView.findViewById(R.id.listStatus);
        if(!Server.accompaniments.isEmpty())
            listStatus.setText("");
        //Adapter fuer die favorites-Liste wird erstellt und festgelegt
        AccompanimentAdapter accompanimentAdapter = new AccompanimentAdapter(getContext(), Server.accompaniments);
        accompanimentsList.setAdapter(accompanimentAdapter);
        //Anweisungen bei Klicken eines Items in der Liste werden festgelegt
        accompanimentsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Accompaniment accompaniment = (Accompaniment) adapterView.getItemAtPosition(i);
                if(accompaniment==null)
                    return;
                Bundle bundle = new Bundle();
                bundle.putParcelable("accompaniment",accompaniment);
                RequestAccompanimentDetailsFragment requestAccompanimentDetailsFragment = new RequestAccompanimentDetailsFragment();
                requestAccompanimentDetailsFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,requestAccompanimentDetailsFragment).commit();
            }
        });
        return contentView;
    }
    public void back() { //Zurueck-Icon fuehrt zur vorherigen Activity zurueck
        RequestServiceFragment requestServiceFragment = new RequestServiceFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,requestServiceFragment).commit();
    }
}
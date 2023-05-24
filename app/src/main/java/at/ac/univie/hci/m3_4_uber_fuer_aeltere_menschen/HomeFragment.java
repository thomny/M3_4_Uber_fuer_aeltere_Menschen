package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    TextView listStatus;
    EscortAdapter escortAdapter;
    private Handler refreshHandler;
    private Runnable refreshRunnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_home, container, false);
        //Zugriff auf die Komponenten in contentView
        TextView userGreeting = contentView.findViewById(R.id.userGreeting);
        listStatus = contentView.findViewById(R.id.listStatus);
        Button requestButton = contentView.findViewById(R.id.requestButton);
        userGreeting.setText("Hallo " + Server.user.getUserName() + '!');
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override //auf SEARCH-Button klicken führt zu Request-Activity
            public void onClick(View view) {
                startRequest();
            }
        });
        //Escort-Listen-Logik
        if(!Server.user.getEscorts().isEmpty()) {
            listStatus.setText("");
        }
        ListView escortsList = contentView.findViewById(R.id.escortsList);
        escortAdapter = new EscortAdapter(getContext(), Server.user.getEscorts());
        escortsList.setAdapter(escortAdapter);
        escortsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override //Klicken auf ein Escort-Item führt zu escortInfo-Activity des jeweiligen Items
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Escort escort = (Escort) adapterView.getItemAtPosition(i);
                if(escort==null)
                    return;
                Intent escortInfo = new Intent(getContext(), EscortInfoActivity.class);
                escortInfo.putExtra("position", ((Integer) Server.user.getEscorts().indexOf(escort)));
                startActivity(escortInfo);
                refresh();
            }
        });

        //Refresh-Logik
        refreshHandler = new Handler();
        refreshRunnable = new Runnable() {
            @Override //alle 5 Sekunden Update
            public void run() {
                refresh();
                refreshHandler.postDelayed(this, 5000);
            }
        };
        refreshHandler.postDelayed(refreshRunnable, 5000);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {            }
        });

        return contentView;
    }
    public void refresh(){ //Liste wird geupdated
        Log.d("REFRESH","Page updated");
        if (escortAdapter != null)
            escortAdapter.notifyDataSetChanged();
        listStatus.setText("");
        if(Server.user.getEscorts().isEmpty())
            listStatus.setText("Noch keine Begleitungen.");
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        refreshHandler.removeCallbacks(refreshRunnable);
    }
    /*@Override
    public void onPause() { //Wechseln des Fragments pausiert Refresh
        super.onPause();
        refreshHandler.removeCallbacks(refreshRunnable);
    }*/


    public void startRequest () {
        //Intent fuer das Wechseln der momentanen Activity zu RequestActivity wird erstellt
        Intent request = new Intent(getContext(), RequestActivity.class);
        startActivity(request);
        listStatus.setText("");
        refresh();
    }
}
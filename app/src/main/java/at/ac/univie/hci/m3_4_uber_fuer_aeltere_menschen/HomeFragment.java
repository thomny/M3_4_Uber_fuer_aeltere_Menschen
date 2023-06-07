package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

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
        ImageButton menu = contentView.findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu(view);
            }
        });
        TextView userGreeting = contentView.findViewById(R.id.userGreeting);
        listStatus = contentView.findViewById(R.id.listStatus);
        Button requestButton = contentView.findViewById(R.id.requestButton);
        userGreeting.setText("Hallo " + Server.user.getUserName() + '!');
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override //auf SEARCH-Button klicken führt zu Request-Activity
            public void onClick(View view) {
                startRequest(contentView);
            }
        });
        //Escort-Listen-Logik
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
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        refreshHandler.removeCallbacks(refreshRunnable);
    }

    public void startRequest (View contentView) {
        //Intent fuer das Wechseln der momentanen Activity zu RequestActivity wird erstellt
        Intent request = new Intent(getContext(), RequestActivity.class);
        startActivity(request);
    }

    public void popupMenu(View view) { //TopBar-Menü
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.topbarmenu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuItem2: logout(); //Abmeldeoption
                        return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    public void logout() { //Abmelden
        Server.user = null;
        Toast.makeText(getContext(), "Erfolgreich abgemeldet.", Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }
}
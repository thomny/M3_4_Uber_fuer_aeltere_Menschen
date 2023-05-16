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

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    EscortAdapter escortAdapter;
    private Handler refreshHandler;
    private Runnable refreshRunnable;

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
        TextView listStatus = contentView.findViewById(R.id.listStatus);
        if(!User.escorts.isEmpty()) {
            listStatus.setText("");
            ListView escortsList = contentView.findViewById(R.id.escortsList);
            //Adapter fuer die Escort-Liste wird erstellt und festgelegt
            escortAdapter = new EscortAdapter(getContext(), User.escorts);
            escortsList.setAdapter(escortAdapter);
            escortsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Escort escort = (Escort) adapterView.getItemAtPosition(i);
                    if(escort==null)
                        return;
                    Intent escortInfo = new Intent(getContext(), EscortInfoActivity.class);
                    escortInfo.putExtra("position", ((Integer) User.escorts.indexOf(escort)));
                    startActivity(escortInfo); //Ausfuehren des Intents
                }
            });
        }
        refreshHandler = new Handler();
        refreshRunnable = new Runnable() {
            @Override
            public void run() {
                refresh();
                refreshHandler.postDelayed(this, 5000);
            }
        };
        refreshHandler.postDelayed(refreshRunnable, 5000);

        return contentView;
    }



    public void startRequest () {
        //Intent fuer das Wechseln der momentanen Activity zu ArtistInfoActivity wird erstellt
        Intent request = new Intent(getContext(), RequestActivity.class);
        startActivity(request); //Ausfuehren des Intents
    }

    public void refresh(){
        Log.d("REFRESH","Page updated");
        if (escortAdapter != null)
            escortAdapter.notifyDataSetChanged();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        refreshHandler.removeCallbacks(refreshRunnable);
    }
    @Override
    public void onPause() {
        super.onPause();
        refreshHandler.removeCallbacks(refreshRunnable);
    }
}
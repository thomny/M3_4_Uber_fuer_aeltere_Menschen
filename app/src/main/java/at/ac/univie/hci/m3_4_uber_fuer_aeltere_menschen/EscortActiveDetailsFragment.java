package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EscortActiveDetailsFragment extends Fragment {
    Escort escort;
    Integer pos;
    ImageButton backButton;
    TextView escortStatus;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_escort_active_details, container, false);

        Bundle bundle = getArguments();
        pos = bundle.getInt("position");
        escort = Server.user.getEscorts().get(pos);
        backButton = contentView.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    back();
                }
        });
        Button cancelButton = contentView.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
        TextView start = contentView.findViewById(R.id.start_location);
        TextView start2 = contentView.findViewById(R.id.start_location2);
        TextView destination = contentView.findViewById(R.id.destination);
        TextView destination2 = contentView.findViewById(R.id.destination2);
        TextView time = contentView.findViewById(R.id.time);
        TextView service = contentView.findViewById(R.id.service);
        TextView accompaniment = contentView.findViewById(R.id.accompaniment);
        escortStatus = contentView.findViewById(R.id.escortStatus);
        start.setText(escort.getStart().getAddressLine1());
        start2.setText(escort.getStart().getAddressLine2());
        destination.setText(escort.getDestination().getAddressLine1());
        destination2.setText(escort.getDestination().getAddressLine2());
        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("HH:mm");
        time.setText(escort.getTime().format(customFormat)+" Uhr");
        service.setText(escort.getService().toString());
        accompaniment.setText(escort.getAccompaniment().toString());

        Button nextButton = contentView.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        return contentView;
    }

    public void cancel(){
        Server.user.getEscorts().remove(escort);
        Intent back = new Intent(getContext(), MainActivity.class);
        startActivity(back);
    }

    public void finish() { //temporaere Loesung -
        Server.user.getFinishedEscorts().add(escort);
        Server.user.getEscorts().remove(escort);
        EscortFinishFragment escortFinishFragment = new EscortFinishFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, escortFinishFragment).commit();
    }

    public void back(){
        Bundle bundle = new Bundle();
        bundle.putInt("position",pos);
        EscortStartFragment escortStartFragment = new EscortStartFragment();
        escortStartFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, escortStartFragment).commit();
    }
}
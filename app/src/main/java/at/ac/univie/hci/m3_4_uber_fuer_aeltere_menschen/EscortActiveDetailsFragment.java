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
        escort = User.escorts.get(pos);
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
        TextView destination = contentView.findViewById(R.id.destination);
        TextView time = contentView.findViewById(R.id.time);
        TextView service = contentView.findViewById(R.id.service);
        TextView accompaniment = contentView.findViewById(R.id.accompaniment);
        escortStatus = contentView.findViewById(R.id.escortStatus);
        start.setText(escort.getStart().toString());
        destination.setText(escort.getDestination().toString());
        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        time.setText(escort.getTime().format(customFormat));
        service.setText(User.escort_request.getService().toString());
        accompaniment.setText(User.escort_request.getAccompaniment().toString());
        escortStatus.setText(escort.getStatus().toString());
        return contentView;
    }

    public void cancel(){
        User.escorts.remove(escort);
        Intent back = new Intent(getContext(), MainActivity.class);
        startActivity(back);
    }

    public void back(){
        Bundle bundle = new Bundle();
        bundle.putInt("position",pos);
        EscortStartFragment escortStartFragment = new EscortStartFragment();
        escortStartFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, escortStartFragment).commit();
    }
}
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
import android.widget.TextView;

import java.time.LocalDateTime;

public class RequestSummaryFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_request_summary, container, false);

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
        if(User.escort_request.getNow())
            User.escort_request.setTime(LocalDateTime.now());

        TextView start = contentView.findViewById(R.id.start_location);
        TextView destination = contentView.findViewById(R.id.destination);
        TextView time = contentView.findViewById(R.id.time);
        TextView service = contentView.findViewById(R.id.service);
        TextView accompaniment = contentView.findViewById(R.id.accompaniment);
        start.setText(User.escort_request.getStart().toString());
        destination.setText(User.escort_request.getDestination().toString());
        time.setText(User.escort_request.getTime().toString());
        service.setText(User.escort_request.getService().toString());
        accompaniment.setText(User.escort_request.getAccompaniment().toString());
        return contentView;
    }

    public void back() { //Zurueck-Icon fuehrt zur vorherigen Activity zurueck
        RequestAccompanimentListFragment requestAccompanimentListFragment = new RequestAccompanimentListFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,requestAccompanimentListFragment).commit();
    }
    public void next() { //temporaere Loesung
        Escort finished_request = new Escort();
        finished_request.setStart(User.escort_request.start);
        finished_request.setDestination(User.escort_request.destination);
        finished_request.setTime(User.escort_request.time);
        finished_request.setService(User.escort_request.service);
        finished_request.setAccompaniment(User.escort_request.accompaniment);
        finished_request.setId();
        //User.escort_request = null;
        User.escorts.add(finished_request);
        Intent back = new Intent(getContext(), MainActivity.class);
        startActivity(back);
    }
}
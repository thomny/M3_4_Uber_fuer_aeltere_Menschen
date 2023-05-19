package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

public class RequestSummaryFragment extends Fragment {
    Dialog finishDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_request_summary, container, false);
        TextView cell1 = contentView.findViewById(R.id.cell1);
        cell1.setBackgroundResource(R.drawable.progress_bar_cell_filled);
        TextView cell2 = contentView.findViewById(R.id.cell2);
        cell2.setBackgroundResource(R.drawable.progress_bar_cell_filled);
        TextView cell3 = contentView.findViewById(R.id.cell3);
        cell3.setBackgroundResource(R.drawable.progress_bar_cell_filled);
        TextView cell4 = contentView.findViewById(R.id.cell4);
        cell4.setBackgroundResource(R.drawable.progress_bar_cell_filled);
        TextView cell5 = contentView.findViewById(R.id.cell5);
        cell5.setBackgroundResource(R.drawable.progress_bar_cell_filled);
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
        TextView start2 = contentView.findViewById(R.id.start_location2);
        TextView destination2 = contentView.findViewById(R.id.destination2);
        TextView time = contentView.findViewById(R.id.time);
        TextView service = contentView.findViewById(R.id.service);
        TextView accompaniment = contentView.findViewById(R.id.accompaniment);
        start.setText(User.escort_request.getStart().getAddressLine1());
        start2.setText(User.escort_request.getStart().getAddressLine2());
        destination.setText(User.escort_request.getDestination().getAddressLine1());
        destination2.setText(User.escort_request.getDestination().getAddressLine2());
        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        time.setText(User.escort_request.getTime().format(customFormat));
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
        User.escorts.add(finished_request);
        Server.accept(finished_request); //Mock

        User.escort_request.setTime(false);

        finishDialog = new Dialog(getContext());
        finishDialog.setContentView(R.layout.request_dialogue_layout);
        finishDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView finishCloseButton = finishDialog.findViewById(R.id.closeButton);
        Button tipButton1 = finishDialog.findViewById(R.id.button1);
        tipButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishDialog.dismiss();
                finish();
            }
        });
        finishCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishDialog.dismiss();
                finish();
            }
        });
        finishDialog.show();
    }

    public void finish (){
        Intent back = new Intent(getContext(), MainActivity.class);
        startActivity(back);
    }
}

/*if (User.escort_request.getNow()) {
            User.escort_request.setTime(false);
            Intent escortInfo = new Intent(getContext(), EscortInfoActivity.class);
            escortInfo.putExtra("position", ((Integer) User.escorts.size()-1));
            startActivity(escortInfo); //Ausfuehren des Intents*/
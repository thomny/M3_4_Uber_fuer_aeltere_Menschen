package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
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
        //Zugriff auf die Komponenten in contentView
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
        ImageView accompanimentPicture = contentView.findViewById(R.id.accompanimentPicture);
        Button nextButton = contentView.findViewById(R.id.nextButton);
        TextView start = contentView.findViewById(R.id.start_location);
        TextView destination = contentView.findViewById(R.id.destination);
        TextView start2 = contentView.findViewById(R.id.start_location2);
        TextView destination2 = contentView.findViewById(R.id.destination2);
        TextView time = contentView.findViewById(R.id.time);
        TextView service = contentView.findViewById(R.id.service);
        TextView accompaniment = contentView.findViewById(R.id.accompaniment);
        //Bei 'JETZT'-Auswahl wird hier die Zeit festgelegt
        if(Server.user.getEscortRequest().getNow())
            Server.user.getEscortRequest().setTime(LocalDateTime.now());
        //ausgewählte Daten werden hier zusammengefasst
        start.setText(Server.user.getEscortRequest().getStart().getAddressLine1());
        start2.setText(Server.user.getEscortRequest().getStart().getAddressLine2());
        destination.setText(Server.user.getEscortRequest().getDestination().getAddressLine1());
        destination2.setText(Server.user.getEscortRequest().getDestination().getAddressLine2());
        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String t = Server.user.getEscortRequest().getTime().format(customFormat) + " Uhr";
        time.setText(t);
        service.setText(Server.user.getEscortRequest().getService().toString());
        accompaniment.setText(Server.user.getEscortRequest().getAccompaniment().toString());
        if(Server.user.getEscortRequest().getAccompaniment().getProfilepicture()!=null)
            accompanimentPicture.setImageDrawable(Server.user.getEscortRequest().getAccompaniment().getProfilepicture());
        // OnClickListener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
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

    public void back() {
        RequestAccompanimentListFragment requestAccompanimentListFragment = new RequestAccompanimentListFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,requestAccompanimentListFragment).commit();
    }
    public void next() {
        //Daten werden in ein neues Escort-Objekt übernommen und in die Escort-Liste des Users eingefügt
        Escort finished_request = new Escort();
        finished_request.setStart(Server.user.getEscortRequest().start);
        finished_request.setDestination(Server.user.getEscortRequest().destination);
        finished_request.setTime(Server.user.getEscortRequest().time);
        finished_request.setService(Server.user.getEscortRequest().service);
        finished_request.setAccompaniment(Server.user.getEscortRequest().accompaniment);
        finished_request.setId();
        Server.user.getEscorts().add(finished_request);
        Server.accept(finished_request); //Mock: ausgewählte Begleitperson bestätigt die Anfrage
        //Dialogfenster: Abschluss einer erfolgreichen Anfrage
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
        //ausgewählte Daten werden bei Abbruch der Anforderung gelöscht
        Server.user.getEscortRequest().setTime(false);
        Server.user.getEscortRequest().setStart(null);
        Server.user.getEscortRequest().setDestination(null);
        Server.user.getEscortRequest().setService(null);
        Server.user.getEscortRequest().setAccompaniment(null);
        getActivity().finish();
    }
}
package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
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
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EscortSummaryFragment extends Fragment {
    Escort escort;
    Integer pos;
    Button nextButton;
    TextView escortStatus;
    ImageView icon;
    Dialog cancelDialog;
    AsyncTask<Void, Void, Void> accompReadyTask;
    boolean startTimer = true;
    Handler refreshHandler;
    Runnable refreshRunnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_escort_summary, container, false);
        //Position des Begleitungs-Items wird aus dem Bundle genommen
        Bundle bundle = getArguments();
        pos = bundle.getInt("position");
        escort = Server.user.getEscorts().get(pos);
        //ist die Begleitung bereits 'aktiv', wird zum nächsten Fragment übersprungen.
        if(escort.getStatus().equals(EscortStatus.ACTIVE))
            next();
        //Zugriff auf die Komponenten in contentView
        Button cancelButton = contentView.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCancelDialog();
            }
        });
        nextButton = contentView.findViewById(R.id.nextButton);
        nextButton.setVisibility(View.GONE);
        TextView start = contentView.findViewById(R.id.start_location);
        TextView start2 = contentView.findViewById(R.id.start_location2);
        TextView destination = contentView.findViewById(R.id.destination);
        TextView destination2 = contentView.findViewById(R.id.destination2);
        TextView time = contentView.findViewById(R.id.time);
        TextView service = contentView.findViewById(R.id.service);
        TextView accompaniment = contentView.findViewById(R.id.accompaniment);
        ImageView accompanimentPicture = contentView.findViewById(R.id.accompanimentPicture);
        escortStatus = contentView.findViewById(R.id.escortStatus);
        start.setText(escort.getStart().getAddressLine1());
        start2.setText(escort.getStart().getAddressLine2());
        destination.setText(escort.getDestination().getAddressLine1());
        destination2.setText(escort.getDestination().getAddressLine2());
        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String t = Server.user.getEscortRequest().getTime().format(customFormat) + " Uhr";
        time.setText(t);
        service.setText(escort.getService().toString());
        accompaniment.setText(escort.getAccompaniment().toString());
        if(escort.getAccompaniment().getProfilepicture()!=null)
            accompanimentPicture.setImageDrawable(escort.getAccompaniment().getProfilepicture());
        icon = contentView.findViewById(R.id.icon);
        //Status-Logik
        if(!(escort.getStatus().equals(EscortStatus.ACCEPTED))) {
            escortStatus.setText("Nicht bestätigt");
            icon.setImageResource(R.drawable.baseline_close_24_red);
        } else {
            escortStatus.setText("Fahrt bestätigt");
            icon.setImageResource(R.drawable.baseline_check_box_24);
        }
        //Bei bestätigter Fahrt und Eintritt der Begleitungs-Zeit wird der nextButton sichtbar
        if(escort.getStatus().equals(EscortStatus.ACCEPTED)
                &&(LocalDateTime.now().isAfter(escort.getTime()) || LocalDateTime.now().isEqual(escort.getTime()))) {
            nextButton.setVisibility(View.VISIBLE);
            buttonLogic();
        }
        //solange die Konditionen zum Start einer Begleitung nicht erfüllt sind, wird alle 5sec geupdated
        if(!escort.getEscortReady()){
            Log.d("if-block","!escort.getEscortReady()");
            refreshHandler = new Handler();
            refreshRunnable = new Runnable() {
                @Override
                public void run() {
                    check();
                    if (!escort.getEscortReady())
                        refreshHandler.postDelayed(this, 5000);
                }
            };
            refreshHandler.postDelayed(refreshRunnable, 5000);
        } //Konditionen sind erfüllt - nextButton wird gezeigt
        else {buttonLogic();Log.d("else-block","escort.getEscortReady()");}
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                getActivity().finish();
            }
        });
        return contentView;
    }

    public void check(){ //Daten werden geupdated
        if(escort.getStatus().equals(EscortStatus.ACCEPTED)) {
            escortStatus.setText("Fahrt bestätigt");
            icon.setImageResource(R.drawable.baseline_check_box_24);
        }
        if(escort.getStatus().equals(EscortStatus.ACCEPTED)
                &&(LocalDateTime.now().isAfter(escort.getTime()) || LocalDateTime.now().isEqual(escort.getTime()))){
            escort.setEscortReady();
            accompReadyTimer();
            buttonLogic();
        }
    }

    public void buttonLogic(){ //Logik des Weiter-Buttons
        //Begleitungs-Status wird geupdated und Button erscheint
        nextButton.setVisibility(View.VISIBLE);
        icon.setImageResource(R.drawable.baseline_close_24_red);
        if(escort.getStatus().equals(EscortStatus.ACCEPTED)){
            escortStatus.setText("Fahrt bestätigt");
            icon.setImageResource(R.drawable.baseline_check_box_24);
        }
        if(escort.getStatus().equals(EscortStatus.ACTIVE)){
            escortStatus.setText("Fahrt aktiv");
            icon.setImageResource(R.drawable.baseline_directions_walk_24);
        }
        //Button-Status
        if (escort.getUserReady() && escort.getAccompReady()) {
            nextButton.setText("Starten");
            nextButton.setBackgroundResource(R.drawable.button);
        }
        if (escort.getUserReady() && !escort.getAccompReady()) {
            nextButton.setText("Warte auf Begleitung ...");
            nextButton.setBackgroundResource(R.drawable.white_button);
        }
        if ((!escort.getUserReady() && escort.getAccompReady()) || (!escort.getUserReady() && !escort.getAccompReady())) {
            nextButton.setText("Bereit");
            nextButton.setBackgroundResource(R.drawable.button);
        }
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!escort.getUserReady() && !escort.getAccompReady()) {
                    escort.setUserReady();
                    nextButton.setText("Warte auf Begleitung ...");
                    nextButton.setBackgroundResource(R.drawable.white_button);
                } else if (!escort.getUserReady() && escort.getAccompReady()) {
                    escort.setUserReady();
                    nextButton.setText("Starten");
                    nextButton.setBackgroundResource(R.drawable.button);
                } else if (escort.getUserReady() && !escort.getAccompReady()) {
                    escort.setUserReady();
                    nextButton.setText("Bereit");
                    nextButton.setBackgroundResource(R.drawable.button);
                } else if (escort.getUserReady() && escort.getAccompReady() && nextButton.getText().equals("Starten"))
                    next(); //wenn User und Begleitperson beide bereit sind, wird die Begleitung gestartet
            }
        });
    }


    public void next() { //Begleitung wird gestartet
            Bundle bundle = new Bundle(); //Position wird an das nächste Fragment weitergegeben
            bundle.putInt("position",pos);
            escort.setStatus(EscortStatus.ACTIVE); //Status wechselt auf 'aktiv'
            EscortStartFragment escortStartFragment = new EscortStartFragment();
            escortStartFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, escortStartFragment).commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(refreshHandler!=null)
            refreshHandler.removeCallbacks(refreshRunnable);
    }
    @Override
    public void onPause() {
        super.onPause();
        if(refreshHandler!=null)
            refreshHandler.removeCallbacks(refreshRunnable);
    }

    public void accompReadyTimer(){ // Mock: Begleitperson stellt sich nach 7sec als 'bereit' verfügbar
        accompReadyTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) { //Timer im Hintergrund
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) { //nachdem der Timer zu Ende ist
                escort.setAccompReady();
                Toast.makeText(getContext(), "Die Begleitung ist bereit.", Toast.LENGTH_SHORT).show();
                if (escort.getUserReady() && escort.getAccompReady()) {
                    nextButton.setText("Starten");
                    nextButton.setBackgroundResource(R.drawable.button);
                }
            }
        };
        accompReadyTask.execute();
    }

    public void openCancelDialog(){
        //Dialogfenster: Stornierungsbestätigung
        cancelDialog = new Dialog(getContext());
        cancelDialog.setContentView(R.layout.cancel_dialogue_layout);
        cancelDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button yesButton = cancelDialog.findViewById(R.id.yesButton);
        Button noButton = cancelDialog.findViewById(R.id.noButton);
        ImageView closeButton = cancelDialog.findViewById(R.id.closeButton);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelDialog.dismiss();
                Server.user.getEscorts().remove(escort);
                getActivity().finish();
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelDialog.dismiss();
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelDialog.dismiss();
            }
        });
        cancelDialog.show();
    }
}
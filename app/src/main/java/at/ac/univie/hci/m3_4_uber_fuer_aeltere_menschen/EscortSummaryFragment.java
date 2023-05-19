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

public class EscortSummaryFragment extends Fragment {
    Escort escort;
    Integer pos;
    Button nextButton;
    TextView escortStatus;
    private AsyncTask<Void, Void, Void> accompReadyTask;
    boolean startTimer = true;
    private Handler refreshHandler;
    private Runnable refreshRunnable;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_escort_summary, container, false);

        Bundle bundle = getArguments();
        pos = bundle.getInt("position");
        escort = User.escorts.get(pos);
        if(escort.getStatus().equals(EscortStatus.ACTIVE))
            next();
        Button cancelButton = contentView.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
        nextButton = contentView.findViewById(R.id.nextButton);
        nextButton.setVisibility(View.GONE);
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
        if(escort.getStatus().equals(EscortStatus.ACCEPTED))
            escortStatus.setTextColor(0xFF4CAF50);
        if(escort.getStatus().equals(EscortStatus.ACCEPTED)
                &&(LocalDateTime.now().isAfter(escort.getTime()) || LocalDateTime.now().isEqual(escort.getTime()))) {
            nextButton.setVisibility(View.VISIBLE);
        }
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
        }
        else {buttonLogic();Log.d("else-block","escort.getEscortReady()");}
        return contentView;
    }

    public void check(){
        Log.d("REFRESH","EscortSummary updated");
        if(escort.getStatus().equals(EscortStatus.ACCEPTED)
                &&(LocalDateTime.now().isAfter(escort.getTime()) || LocalDateTime.now().isEqual(escort.getTime()))){
            escort.setEscortReady();
            accompReadyTimer();
            buttonLogic();
        }
    }

    public void buttonLogic(){
        Log.d("buttonLogic","entered buttonLogic");
        nextButton.setVisibility(View.VISIBLE);
        escortStatus.setTextColor(0xFF4CAF50);
        escortStatus.setText(escort.getStatus().toString());
        if (escort.getUserReady() && escort.getAccompReady())
            nextButton.setText("Starten");
        if (escort.getUserReady() && !escort.getAccompReady())
            nextButton.setText("Warten");
        if (!escort.getUserReady() && escort.getAccompReady())
            nextButton.setText("Bereit");
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!escort.getUserReady() && !escort.getAccompReady()) {
                    escort.setUserReady();
                    nextButton.setText("Warten");
                } else if (!escort.getUserReady() && escort.getAccompReady()) {
                    escort.setUserReady();
                    nextButton.setText("Starten");
                } else if (escort.getUserReady() && !escort.getAccompReady()) {
                    escort.setUserReady();
                    nextButton.setText("Bereit");
                } else if (escort.getUserReady() && escort.getAccompReady() && nextButton.getText().equals("Starten"))
                    next();
            }
        });
    }


    public void next() { //temporaere Loesung -
            Bundle bundle = new Bundle();
            bundle.putInt("position",pos);
            escort.setStatus(EscortStatus.ACTIVE);
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

    public void accompReadyTimer(){
        accompReadyTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                escort.setAccompReady();
                if (escort.getUserReady() && escort.getAccompReady())
                    nextButton.setText("Starten");
            }
        };
        accompReadyTask.execute();
    }

    public void cancel(){
        User.escorts.remove(escort);
        Intent back = new Intent(getContext(), MainActivity.class);
        startActivity(back);
    }
}
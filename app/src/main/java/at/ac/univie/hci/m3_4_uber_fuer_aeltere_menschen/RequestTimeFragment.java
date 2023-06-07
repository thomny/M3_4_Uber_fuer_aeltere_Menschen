package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.TimeZone;

public class RequestTimeFragment extends Fragment {
    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
    Button customButton;
    CalendarView calendarView;
    TimePicker timePicker;
    DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("dd.MMMM   HH:mm");
    int day;
    int month;
    int year;
    int hour;
    int minute;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_request_time, container, false);
        //Zugriff auf die Komponenten in contentView
        ImageButton backButton = contentView.findViewById(R.id.backButton);
        Button nowButton = contentView.findViewById(R.id.nowButton);
        Button laterButton = contentView.findViewById(R.id.laterButton);
        TextView textView2 = contentView.findViewById(R.id.textView2);
        customButton = contentView.findViewById(R.id.customButton);
        calendarView = contentView.findViewById(R.id.calendarView);
        timePicker = contentView.findViewById(R.id.timePicker);
        TextView cell1 = contentView.findViewById(R.id.cell1);
        cell1.setBackgroundResource(R.drawable.progress_bar_cell_filled);
        TextView cell2 = contentView.findViewById(R.id.cell2);
        cell2.setBackgroundResource(R.drawable.progress_bar_cell_filled);
        //aktuelle Zeitdaten
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH)+1;
        year = calendar.get(Calendar.YEAR);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        //bevor 'laterButton' ausgelöst wird, werden diese Elemente vom User versteckt
        customButton.setVisibility(View.GONE);
        calendarView.setVisibility(View.GONE);
        timePicker.setVisibility(View.GONE);
        textView2.setVisibility(View.GONE);
        laterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Elemente werden gezeigt
                textView2.setVisibility(View.VISIBLE);
                laterButton.setVisibility(View.GONE);
                calendarView.setVisibility(View.VISIBLE);
                timePicker.setVisibility(View.VISIBLE);
                customButton.setVisibility(View.VISIBLE);
                ScrollView scrollView = contentView.findViewById(R.id.scrollView);
                scrollView.post(new Runnable() {
                    @Override // ScrollView springt automatisch auf die gewünschte Position
                    public void run() {
                        scrollView.smoothScrollTo(0, textView2.getTop());
                    }
                });
                customTimeSet(contentView); //manuelle Auswahl der Zeit
            }
        });
        nowButton.setOnClickListener(new View.OnClickListener() {
            @Override //nowButton wählt jetzige Zeit als Escort-Zeit aus
            public void onClick(View view) {
                Server.user.getEscortRequest().setTime(true);
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

    public void customTimeSet(View contentView){ //Logik für manuelle Zeitauswahl
        String time = LocalDateTime.of(year,month,day,hour,minute).format(customFormat) + " Uhr";
        customButton.setText(time);
        customButton.setOnClickListener(new View.OnClickListener() {
            @Override //solange (ausgewählte Zeit >= jetzige Zeit) wird neue Zeit akzeptiert
            public void onClick(View view) {
                if(calendar.get(Calendar.DAY_OF_MONTH)==day
                        &&(calendar.get(Calendar.MONTH)+1)==month
                        &&(calendar.get(Calendar.HOUR_OF_DAY)>hour ||
                        (calendar.get(Calendar.HOUR_OF_DAY)==hour && calendar.get(Calendar.MINUTE)>minute))){
                    Toast.makeText(getContext(), "Ausgewählte Zeit ist früher als jetzt.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Server.user.getEscortRequest().setTime(LocalDateTime.of(year,month,day,hour,minute));
                next();
            }
        });
        calendarView.setMinDate(calendar.getTimeInMillis()); //Mindestdatum von calendarView wird auf jetzt gesetzt
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override //Änderung der Auswahl aktualisiert Daten
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                year = i;
                month = i1+1;
                day = i2;
                String time = LocalDateTime.of(year,month,day,hour,minute).format(customFormat) + " Uhr";
                customButton.setText(time); //'customButton'-Text wird mit ausgewählten Daten aktualisiert
            }
        });
        //timePicker wird mit jetzigen Daten initialisiert
        timePicker.setIs24HourView(true);
        timePicker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setMinute(calendar.get(Calendar.MINUTE));
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override //Änderung der Auswahl aktualisiert Daten
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                hour = i;
                minute = i1;
                //'customButton'-Text wird mit ausgewählten Daten aktualisiert
                String time = LocalDateTime.of(year,month,day,hour,minute).format(customFormat) + " Uhr";
                customButton.setText(time);
            }
        });
    }

    public void back() { //Zurueck-Icon fuehrt zur MainActivity zurueck
        RequestDestinationFragment requestDestinationFragment = new RequestDestinationFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,requestDestinationFragment).commit();
    }
    public void next() {
        RequestServiceFragment requestServiceFragment = new RequestServiceFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,requestServiceFragment).commit();
    }
}
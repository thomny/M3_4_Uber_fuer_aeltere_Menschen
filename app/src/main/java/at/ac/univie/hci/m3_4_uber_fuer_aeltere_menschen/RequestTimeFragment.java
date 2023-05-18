package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.animation.ObjectAnimator;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.Month;
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
        View contentView = inflater.inflate(R.layout.fragment_request_time2, container, false);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH)+1;
        year = calendar.get(Calendar.YEAR);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        ImageButton backButton = contentView.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        Button nowButton = contentView.findViewById(R.id.nowButton);
        Button laterButton = contentView.findViewById(R.id.laterButton);
        TextView textView2 = contentView.findViewById(R.id.textView2);
        customButton = contentView.findViewById(R.id.customButton);
        customButton.setVisibility(View.GONE);
        calendarView = contentView.findViewById(R.id.calendarView);
        calendarView.setVisibility(View.GONE);
        timePicker = contentView.findViewById(R.id.timePicker);
        timePicker.setVisibility(View.GONE);
        textView2.setVisibility(View.GONE);
        laterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView2.setVisibility(View.VISIBLE);
                laterButton.setVisibility(View.GONE);
                calendarView.setVisibility(View.VISIBLE);
                timePicker.setVisibility(View.VISIBLE);
                customButton.setVisibility(View.VISIBLE);
                ScrollView scrollView = contentView.findViewById(R.id.scrollView);
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.smoothScrollTo(0, textView2.getTop());
                    }
                });
                customTimeSet(contentView);
            }
        });
        nowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.escort_request.setTime(true);
                next();
            }
        });

        return contentView;
    }

    public void customTimeSet(View contentView){
        customButton.setText(LocalDateTime.of(year,month,day,hour,minute).format(customFormat));
        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(calendar.get(Calendar.DAY_OF_MONTH)==day
                        &&(calendar.get(Calendar.MONTH)+1)==month
                        &&(calendar.get(Calendar.HOUR_OF_DAY)>hour ||
                        (calendar.get(Calendar.HOUR_OF_DAY)==hour && calendar.get(Calendar.MINUTE)>minute))){
                    Toast.makeText(getContext(), "Ausgewählte Zeit ist früher als jetzt.", Toast.LENGTH_SHORT).show();
                    return;
                }

                User.escort_request.setTime(LocalDateTime.of(year,month,day,hour,minute));
                next();
            }
        });
        calendarView.setMinDate(calendar.getTimeInMillis());
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                year = i;
                month = i1+1;
                day = i2;
                String time = LocalDateTime.of(year,month,day,hour,minute).format(customFormat);
                customButton.setText(time);
            }
        });
        timePicker.setIs24HourView(true);
        timePicker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setMinute(calendar.get(Calendar.MINUTE));
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                hour = i;
                minute = i1;
                customButton.setText(LocalDateTime.of(year,month,day,hour,minute).format(customFormat));
            }
        });
    }

    public void back() { //Zurueck-Icon fuehrt zur vorherigen Activity zurueck
        RequestDestinationFragment requestDestinationFragment = new RequestDestinationFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,requestDestinationFragment).commit();
    }
    public void next() { //temporaere Loesung
        RequestServiceFragment requestServiceFragment = new RequestServiceFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,requestServiceFragment).commit();
    }
}
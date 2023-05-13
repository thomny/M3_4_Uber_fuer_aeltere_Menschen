package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.TimeZone;

public class RequestTimeFragment extends Fragment {
    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
    int day;
    int month;
    int year;
    int hour;
    int minute;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_request_time, container, false);
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
        Button customButton = contentView.findViewById(R.id.customButton);
        nowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.escort_request.setTime(true);
                next();
            }
        });
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
        CalendarView calendarView = contentView.findViewById(R.id.calendarView);
        calendarView.setMinDate(calendar.getTimeInMillis());
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                year = i;
                month = i1+1;
                day = i2;
            }
        });
        TimePicker timePicker = contentView.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        timePicker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setMinute(calendar.get(Calendar.MINUTE));
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                hour = i;
                minute = i1;
            }
        });
        return contentView;
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



/*package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.nfc.TagLostException;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.time.LocalDateTime;
import java.util.Calendar;

public class RequestTimeFragment extends Fragment {
    int day;
    int month;
    int year;
    int hour;
    int min;
    Calendar calendar = Calendar.getInstance();
    int currDay = calendar.get(Calendar.DAY_OF_MONTH);
    int currMonth = calendar.get(Calendar.MONTH);
    TextView dateTextView;
    TextView timeTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_request_time, container, false);
        ImageButton backButton = contentView.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        Button nowButton = contentView.findViewById(R.id.nowButton);
        Button customButton = contentView.findViewById(R.id.customButton);
        nowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.escort_request.setTime(true);
                next();
            }
        });
        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.escort_request.setTime(LocalDateTime.of(year,month,day,hour,min));
                next();
            }
        });
        dateTextView = contentView.findViewById(R.id.dateTextView);
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate();
            }
        });
        timeTextView = contentView.findViewById(R.id.timeTextView);
        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime();
            }
        });
        return contentView;
    }

    public void back() { //Zurueck-Icon fuehrt zur vorherigen Activity zurueck
        RequestDestinationFragment requestDestinationFragment = new RequestDestinationFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,requestDestinationFragment).commit();
    }
    public void next() { //temporaere Loesung

        RequestServiceFragment requestServiceFragment = new RequestServiceFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,requestServiceFragment).commit();
    }

    public void setDate(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                year = i;
                month = i1+1;
                day = i2;
                dateTextView.setText("changed");
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    public void setTime(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), 0,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                hour = i;
                min = i1;
                if(currDay==day&&currMonth==month) {
                    timePicker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
                    timePicker.setMinute(calendar.get(Calendar.MINUTE));
                }
                timeTextView.setText("changed");
            }
        },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true);

        timePickerDialog.show();
    }
}*/
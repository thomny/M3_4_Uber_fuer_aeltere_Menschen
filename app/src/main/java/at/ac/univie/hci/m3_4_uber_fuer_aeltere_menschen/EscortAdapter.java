package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EscortAdapter extends ArrayAdapter<Escort> {
    //Konstruktor mit dem Context und der anzuzeigenden Liste
    public EscortAdapter(@NonNull Context context, @NonNull ArrayList<Escort> objects) {
        super(context, R.layout.escort_item, objects); //Layout fuer einzelne Items werden hier angegeben
    }

    @NonNull
    @Override //EscortAdapter legt fest, wie eine Begleitung in einer Liste dargestellt wird
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Escort escort = getItem(position); //Begleitung in der jetzigen Position wird initialisiert
        if(convertView==null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.escort_item,parent,false);
        //Zugriff auf die einzelnen Komponenten eines Begleitungs-Listenitems
        TextView escortText = convertView.findViewById(R.id.textView);
        TextView escortDestination = convertView.findViewById(R.id.escortDestination);
        TextView escortTime = convertView.findViewById(R.id.escortTime);
        TextView escortStatus = convertView.findViewById(R.id.escortStatus);
        ImageView icon = convertView.findViewById(R.id.icon);
        //Komponenten werden mit den Informationen der Begleitung an der jetzigen Position gefuellt
        escortDestination.setText(escort.getDestination().getAddressLine1());
        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("dd.MM., HH:mm");
        DateTimeFormatter customFormat2 = DateTimeFormatter.ofPattern("HH:mm");
        String day = escort.getTime().format(customFormat) + " Uhr";
        if(escort.getTime().toLocalDate().isEqual(LocalDate.now()))
            day = "Heute, "+escort.getTime().format(customFormat2)+" Uhr";
        if(escort.getTime().isEqual(LocalDateTime.now())||escort.getTime().isBefore(LocalDateTime.now()))
            day = "Jetzt ("+escort.getTime().format(customFormat2)+" Uhr)";
        if((escort.getTime().toLocalDate()).isEqual(LocalDate.now().plusDays(1)))
            day = "Morgen, "+escort.getTime().format(customFormat2)+" Uhr";
        escortTime.setText(day);
        String text = escort.getAccompaniment().getName() + " wird Sie um "
                + escort.getTime().format(customFormat2) + " Uhr von " + escort.getStart().getAddressLine1() + " abholen.";
        escortStatus.setText("Nicht bestätigt");
        icon.setImageResource(R.drawable.baseline_close_24_red);
        escortText.setText("Es wird auf die Bestätigung von " + escort.getAccompaniment().getName() + " gewartet.");
        if(escort.getStatus().equals(EscortStatus.ACCEPTED)){
            escortStatus.setText("Fahrt bestätigt");
            icon.setImageResource(R.drawable.baseline_check_box_24);
            escortText.setText(text);
        }
        if(escort.getStatus().equals(EscortStatus.ACTIVE)){
            escortStatus.setText("Fahrt aktiv");
            icon.setImageResource(R.drawable.baseline_directions_walk_24);
            escortText.setText(text);
        }
        return convertView;
    }
}

/*    @NonNull
    @Override //EscortAdapter legt fest, wie eine Begleitung in einer Liste dargestellt wird
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Escort escort = getItem(position); //Begleitung in der jetzigen Position wird initialisiert
        if(convertView==null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.escort_item,parent,false);
        //Zugriff auf die einzelnen Komponenten eines Begleitungs-Listenitems
        TextView escortStart = convertView.findViewById(R.id.escortStart);
        TextView escortDestination = convertView.findViewById(R.id.escortDestination);
        TextView escortTime = convertView.findViewById(R.id.escortTime);
        TextView escortService = convertView.findViewById(R.id.escortService);
        TextView escortAccompaniment = convertView.findViewById(R.id.escortAccompaniment);
        TextView escortStatus = convertView.findViewById(R.id.escortStatus);
        ImageView icon convertView.findViewById(R.id.icon);
        //Komponenten werden mit den Informationen der Begleitung an der jetzigen Position gefuellt
        //escortStart.setText(escort.getStart().toString());
        escortStart.setText(escort.getStart().toString());
        escortDestination.setText(escort.getDestination().toString());
        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        escortTime.setText(escort.getTime().format(customFormat));
        escortService.setText(escort.getService().toString());
        escortAccompaniment.setText(escort.getAccompaniment().toString());
        escortAccompaniment.setText(escort.getAccompaniment().toString());
        escortStatus.setText(escort.getStatus().toString());
        return convertView;
    }
}*/
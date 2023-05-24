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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EscortAdapter extends ArrayAdapter<Escort> { //Adapter für Begleitungen/Fahrten
    public EscortAdapter(@NonNull Context context, @NonNull ArrayList<Escort> objects) {
        super(context, R.layout.escort_item, objects); //Layout fuer einzelne Items werden hier angegeben
    }

    @NonNull
    @Override //Zugriff auf die einzelnen Komponenten eines Begleitungs-Listenitems
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Escort escort = getItem(position); //Begleitung in der jetzigen Position wird initialisiert
        if(convertView==null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.escort_item,parent,false);
        //Komponenten werden mit den Informationen der Begleitung an der jetzigen Position gefuellt
        TextView escortText = convertView.findViewById(R.id.textView);
        TextView escortDestination = convertView.findViewById(R.id.escortDestination);
        TextView escortTime = convertView.findViewById(R.id.escortTime);
        TextView escortStatus = convertView.findViewById(R.id.escortStatus);
        ImageView icon = convertView.findViewById(R.id.icon);
        escortDestination.setText(escort.getDestination().getAddressLine1());
        //Formatierung der Zeitangabe
        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("dd.MM., HH:mm");
        DateTimeFormatter customFormat2 = DateTimeFormatter.ofPattern("HH:mm");
        String day = escort.getTime().format(customFormat) + " Uhr";
        //abhängig vom Datum wird die Ausgabe personalisiert
        if(escort.getTime().toLocalDate().isEqual(LocalDate.now()))
            day = "Heute, "+escort.getTime().format(customFormat2)+" Uhr";
        if(escort.getTime().isEqual(LocalDateTime.now())||escort.getTime().isBefore(LocalDateTime.now()))
            day = "Jetzt ("+escort.getTime().format(customFormat2)+" Uhr)";
        if((escort.getTime().toLocalDate()).isEqual(LocalDate.now().plusDays(1)))
            day = "Morgen, "+escort.getTime().format(customFormat2)+" Uhr";
        escortTime.setText(day);
        //personalisierter Text
        String text = escort.getAccompaniment().getName() + " wird Sie um "
                + escort.getTime().format(customFormat2) + " Uhr von " + escort.getStart().getAddressLine1() + " abholen.";
        escortStatus.setText("Nicht bestätigt");
        //Statusanzeige-Logik
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
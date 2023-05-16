package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

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
        TextView escortStart = convertView.findViewById(R.id.escortStart);
        TextView escortDestination = convertView.findViewById(R.id.escortDestination);
        TextView escortTime = convertView.findViewById(R.id.escortTime);
        TextView escortService = convertView.findViewById(R.id.escortService);
        TextView escortAccompaniment = convertView.findViewById(R.id.escortAccompaniment);
        TextView escortStatus = convertView.findViewById(R.id.escortStatus);
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
}
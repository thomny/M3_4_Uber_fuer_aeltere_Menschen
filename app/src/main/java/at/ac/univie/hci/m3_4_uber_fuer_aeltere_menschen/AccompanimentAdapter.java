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

import java.util.ArrayList;

public class AccompanimentAdapter extends ArrayAdapter<Accompaniment> { //Adapter für Begleitpersonen
    public AccompanimentAdapter(@NonNull Context context, @NonNull ArrayList<Accompaniment> objects) {
        super(context, R.layout.accompaniment_item, objects); //Layout fuer einzelne Items werden hier angegeben
    }

    @NonNull //Zugriff auf die einzelnen Komponenten eines Begleitpersonen-Listenitems
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Accompaniment accompaniment = getItem(position); //Begleitperson in der jetzigen Position wird initialisiert
        if(convertView==null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.accompaniment_item,parent,false);
        //Komponenten werden mit den Informationen der Begleitperson an der jetzigen Position gefuellt
        ImageView accompanimentPicture = convertView.findViewById(R.id.accompanimentPicture);
        TextView accompanimentName = convertView.findViewById(R.id.accompanimentName);
        accompanimentName.setText(accompaniment.getName());
        if(accompaniment.getProfilepicture()!=null)
            accompanimentPicture.setImageDrawable(accompaniment.getProfilepicture());
        else accompanimentPicture.setImageResource(R.drawable.baseline_person_24);
        return convertView;
    }
}
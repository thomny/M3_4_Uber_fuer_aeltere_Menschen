package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
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

public class AddressSearchAdapter extends ArrayAdapter<Address> {
    //Konstruktor mit dem Context und der anzuzeigenden Liste
    public AddressSearchAdapter(@NonNull Context context, @NonNull ArrayList<Address> objects) {
        super(context, R.layout.address_search_item, objects); //Layout fuer einzelne Items werden hier angegeben
    }

    @NonNull
    @Override //EscortAdapter legt fest, wie eine Begleitung in einer Liste dargestellt wird
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Address address = getItem(position); //Begleitung in der jetzigen Position wird initialisiert
        if(convertView==null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.address_search_item,parent,false);
        //Zugriff auf die einzelnen Komponenten eines Begleitungs-Listenitems
        TextView addressLine1 = convertView.findViewById(R.id.addressLine1);
        TextView addressLine2 = convertView.findViewById(R.id.addressLine2);
        //Komponenten werden mit den Informationen der Begleitung an der jetzigen Position gefuellt
        addressLine1.setText(address.getAddressLine1());
        addressLine2.setText(address.getAddressLine2());
        addressLine1.setTextColor(Color.BLACK);
        addressLine2.setTextColor(Color.BLACK);
        if(address.equals(User.escort_request.start)){
            addressLine1.setTextColor(0xFFCCCCCC);
            addressLine2.setTextColor(0xFFCCCCCC);
        }
        return convertView;
    }
}
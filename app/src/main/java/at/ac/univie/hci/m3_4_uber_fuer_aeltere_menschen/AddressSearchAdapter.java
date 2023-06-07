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

import java.util.ArrayList;

public class AddressSearchAdapter extends ArrayAdapter<Address> { //Adapter für Addressen in der Suchleiste
    public AddressSearchAdapter(@NonNull Context context, @NonNull ArrayList<Address> objects) {
        super(context, R.layout.address_search_item, objects); //Layout fuer einzelne Items werden hier angegeben
    }
    @NonNull
    @Override //Zugriff auf die einzelnen Komponenten eines Addressen-Listenitems
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Address address = getItem(position); //Addresse in der jetzigen Position wird initialisiert
        if(convertView==null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.address_search_item,parent,false);
        TextView current = convertView.findViewById(R.id.current);
        current.setVisibility(View.VISIBLE);
        if(!address.getCurrent()){ //ist die Addresse als 'momentaner Standort' gekennzeichnet,
            current.setVisibility(View.GONE); // wird sie auch in der Liste dementsprechend markiert
        }
        //Komponenten werden mit den Informationen der Addresse an der jetzigen Position gefuellt
        TextView addressLine1 = convertView.findViewById(R.id.addressLine1);
        TextView addressLine2 = convertView.findViewById(R.id.addressLine2);
        ImageView nextButton = convertView.findViewById(R.id.nextButton);
        addressLine1.setText(address.getAddressLine1());
        addressLine2.setText(address.getAddressLine2());
        addressLine1.setTextColor(Color.BLACK);
        addressLine2.setTextColor(Color.BLACK);
        nextButton.setVisibility(View.VISIBLE);
        //Ist die Addresse bereits in RequestStartFragment ausgewählt, wird sie gekennzeichnet
        if(address.equals(Server.user.getEscortRequest().start)){
            addressLine1.setTextColor(0xFFCCCCCC);
            addressLine2.setTextColor(0xFFCCCCCC);
            nextButton.setVisibility(View.GONE);
        }
        return convertView;
    }
}
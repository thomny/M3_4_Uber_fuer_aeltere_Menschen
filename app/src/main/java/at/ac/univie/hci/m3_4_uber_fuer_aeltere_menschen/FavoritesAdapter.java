package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class FavoritesAdapter extends ArrayAdapter<Address> {
    public FavoritesAdapter(@NonNull Context context, @NonNull ArrayList<Address> objects) {
        super(context, R.layout.favorites_item, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Address address = getItem(position);
        if(convertView==null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.favorites_item,parent,false);
        TextView favorite = convertView.findViewById(R.id.favorite);
        favorite.setText(address.getAddressLine1());
        return convertView;
    }
}
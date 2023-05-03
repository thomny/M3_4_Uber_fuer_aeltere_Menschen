package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;

public class AccountFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_account, container, false);
        //Zugriff auf die Komponenten in contentView
        //SearchView searched = contentView.findViewById(R.id.searchbar);

        return contentView;
    }
}
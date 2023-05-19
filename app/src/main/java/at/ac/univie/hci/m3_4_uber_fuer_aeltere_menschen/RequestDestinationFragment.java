package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RequestDestinationFragment extends Fragment {
    private Handler searchHandler = new Handler();
    private Runnable searchRunnable;
    Address current = new Address("Aktueller Standort","Ihre momentante Position"); //mocking
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_request_destination, container, false);
        TextView cell1 = contentView.findViewById(R.id.cell1);
        cell1.setBackgroundResource(R.drawable.progress_bar_cell_filled);
        ImageButton backButton = contentView.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        SearchView searchbar = contentView.findViewById(R.id.searchbar);
        ListView suggestionListView = contentView.findViewById(R.id.listView);
        ArrayList<Address> suggestions = new ArrayList<Address>();
        ArrayList<Address> default_suggestions = new ArrayList<Address>();
        default_suggestions.addAll(User.address_favorites);
        default_suggestions.addAll(User.address_history);
        default_suggestions.add(0,current);
        AddressSearchAdapter addressSearchAdapter = new AddressSearchAdapter(getContext(),suggestions);
        suggestionListView.setAdapter(addressSearchAdapter);
        addressSearchAdapter.addAll(default_suggestions);
        searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                if(s.isEmpty()) {
                    addressSearchAdapter.clear();
                    addressSearchAdapter.addAll(default_suggestions);
                }

                searchHandler.removeCallbacks(searchRunnable);

                searchRunnable = new Runnable() {
                    @Override
                    public void run() {
                        if (!(s.isEmpty())) {
                            ArrayList<Address> updatedSuggestions = null;
                            try {
                                updatedSuggestions = AddressAutocompleteAPI.getAddress(s);
                            } catch (ExecutionException e) {
                                throw new RuntimeException(e);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            addressSearchAdapter.clear();
                            addressSearchAdapter.addAll(updatedSuggestions);
                            addressSearchAdapter.notifyDataSetChanged();
                        }
                    }
                };
                searchHandler.postDelayed(searchRunnable, 500);
                return true;
            }
        });
        suggestionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Address address = (Address) adapterView.getItemAtPosition(i);
                if(address.equals(User.escort_request.start)) {
                    Toast.makeText(getContext(), "Startaddresse darf nicht Zieladdresse sein.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if((!(address.equals(current)))&&(!(User.address_favorites.contains(address)))&&(!(User.address_history.contains(address)))) { //problem
                    User.address_history.add(address);
                }
                User.escort_request.setDestination(address);
                next();
            }
        });
        return contentView;
    }

    public void back() { //Zurueck-Icon fuehrt zur vorherigen Activity zurueck
        RequestStartFragment requestStartFragment = new RequestStartFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,requestStartFragment).commit();
    }
    public void next() { //temporaere Loesung
        RequestTimeFragment requestTimeFragment = new RequestTimeFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,requestTimeFragment).commit();
    }
}
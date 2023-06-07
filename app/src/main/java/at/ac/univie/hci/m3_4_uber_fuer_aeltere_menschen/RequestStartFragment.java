package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RequestStartFragment extends Fragment {
    private Handler searchHandler = new Handler();
    private Runnable searchRunnable;
    Address current;
    LocationManager locationManager;
    LocationListener locationListener;
    boolean first = true;
    ArrayList<Address> default_suggestions;
    ArrayList<Address> suggestions;
    AddressSearchAdapter addressSearchAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_request_start, container, false);
        //Zugriff auf die Komponenten in contentView
        SearchView searchbar = contentView.findViewById(R.id.searchbar);
        ListView suggestionListView = contentView.findViewById(R.id.listView);
        //Listen werden erstellt
        suggestions = new ArrayList<Address>(); //Suchvorschläge
        default_suggestions = new ArrayList<Address>(); //default-Liste=Favoriten+Suchverlauf
        default_suggestions.addAll(Server.user.getAddressFavorites()); //Favoriten
        default_suggestions.addAll(Server.user.getAddressHistory()); //Suchverlauf
        addressSearchAdapter = new AddressSearchAdapter(getContext(), suggestions);
        suggestionListView.setAdapter(addressSearchAdapter);
        addressSearchAdapter.addAll(default_suggestions);
        //Suchbar- und Ergebniss-Logik
        searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.isEmpty()) { //leere Suchanfrage zeigt Favoriten+Suchverlauf als Ergebniss
                    addressSearchAdapter.clear();
                    addressSearchAdapter.addAll(default_suggestions);
                }
                // nachdem für eine gewisse Zeit nichts eingegeben wird, wird nach der Anfrage gesucht,
                // die sich zu diesem Zeitpunkt in der Suchleiste befindet
                searchHandler.removeCallbacks(searchRunnable);
                searchRunnable = new Runnable() {
                    @Override
                    public void run() {
                        if (!(s.isEmpty())) {
                            ArrayList<Address> updatedSuggestions = null; //NeueVorschläge-Liste
                            try {
                                // String in der Suchleiste wird an die Geoapify-Autocomplete-API weitergegeben
                                // und NeueVorschläge-Liste wird mit Ergebnissen befüllt
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
                searchHandler.postDelayed(searchRunnable, 500); //gewisse Zeit = 0.5sec
                return true;
            }
        });
        suggestionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override //Klicken auf ein Addressen-Item fügt dieses zu Escort-Request hinzu
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Address address = (Address) adapterView.getItemAtPosition(i);
                if ((!(address.equals(current))) && (!(Server.user.getAddressFavorites().contains(address))) && (!(Server.user.getAddressHistory().contains(address)))) { //problem
                    Server.user.getAddressHistory().add(address);
                }
                Server.user.getEscortRequest().setStart(address);
                next(); //es wird zum nächsten Schritt gewechselt
            }
        });
        // nach Standortermittlungs-Erlaubnis fragen
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        locationTracking(); // Standortermittlungs-Logik
        //durch 'android:iconifiedByDefault="false"' wird die Tastatur automatisch geoeffnet
        contentView.post(new Runnable() { //workaround
            @Override
            public void run() { //nachdem view initialisiert wurde, wird die Tastatur sofort geschlossen
                searchbar.clearFocus();
            }
        });
        //Android-Back-Button wird überschrieben
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                getActivity().finish();
            }
        });

        return contentView;
    }

    public void next() {
        RequestDestinationFragment requestDestinationFragment = new RequestDestinationFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, requestDestinationFragment).commit();
    }

    public void locationTracking() { // Standortermittlungs-Logik
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) { //Koordinaten werden im Hintergrund geupdated
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                //Bei erstmaligen Einstieg in das Fragment wird der aktuelle Standort ermittelt
                if(first) {
                    try {
                        // aktuelle Koordinaten werden an die Geoapify-ReverseGeocoding-API weitergegeben
                        current = AddressAutocompleteAPI.getCurrentAddress(Double.toString(latitude),Double.toString(longitude));
                        current.setCurrent(true);
                        if (!default_suggestions.contains(current)) {
                            // und die momentante Addresse wird in die default-Liste eingefügt
                            default_suggestions.add(0, current);
                            addressSearchAdapter.clear();
                            addressSearchAdapter.addAll(default_suggestions);
                            addressSearchAdapter.notifyDataSetChanged();
                        }
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    first = false;
                }
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {            }
            @Override
            public void onProviderEnabled(String provider) {            }
            @Override
            public void onProviderDisabled(String provider) {            }
        };
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }
}
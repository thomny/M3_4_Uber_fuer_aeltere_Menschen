package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

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
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RequestDestinationFragment extends Fragment {
    private Handler searchHandler = new Handler();
    private Runnable searchRunnable;
    Address current;
    LocationManager locationManager;
    LocationListener locationListener;
    boolean first = true;
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
        default_suggestions.addAll(Server.user.getAddressFavorites());
        default_suggestions.addAll(Server.user.getAddressHistory());
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
                if(address.equals(Server.user.escortRequest.start)) {
                    Toast.makeText(getContext(), "Startaddresse darf nicht Zieladdresse sein.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if((!(address.equals(current)))&&(!(Server.user.getAddressFavorites().contains(address)))&&(!(Server.user.getAddressHistory().contains(address)))) { //problem
                    Server.user.getAddressHistory().add(address);
                }
                Server.user.escortRequest.setDestination(address);
                next();
            }
        });

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                Log.d("lat",Double.toString(latitude));
                Log.d("lon",Double.toString(longitude));
                if(first) {
                    Log.d("first","true");
                    try {
                        current = AddressAutocompleteAPI.getCurrentAddress(Double.toString(latitude),Double.toString(longitude));
                        current.setCurrent(true);
                        if (!default_suggestions.contains(current)) {
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
                    Log.d("current",current.toString());
                    first = false;
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        //durch 'android:iconifiedByDefault="false"' wird die Tastatur automatisch geoeffnet
        contentView.post(new Runnable() { //workaround
            @Override
            public void run() { //nachdem view initialisiert wurde, wird die Tastatur sofort geschlossen
                searchbar.clearFocus();
            }
        });
        return contentView;
    }

    public void back() { //Zurueck-Icon fuehrt zur vorherigen Activity zurueck
        Server.user.escortRequest.setStart(null);
        RequestStartFragment requestStartFragment = new RequestStartFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,requestStartFragment).commit();
    }
    public void next() { //temporaere Loesung
        RequestTimeFragment requestTimeFragment = new RequestTimeFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,requestTimeFragment).commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }
}
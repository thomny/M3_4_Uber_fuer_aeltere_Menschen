package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import java.util.Objects;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class CurrentLocation implements LocationListener {
    private Location lastLocation;

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
    }

    public double getLatitude() {
        if (lastLocation != null) {
            return lastLocation.getLatitude();
        }
        return 0.0;
    }

    public double getLongitude() {
        if (lastLocation != null) {
            return lastLocation.getLongitude();
        }
        return 0.0;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {    }

    @Override
    public void onProviderEnabled(String provider) {    }

    @Override
    public void onProviderDisabled(String provider) {    }
}

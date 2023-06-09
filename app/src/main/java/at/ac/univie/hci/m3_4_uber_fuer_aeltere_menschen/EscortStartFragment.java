package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;

public class EscortStartFragment extends Fragment {
    Escort escort;
    Integer pos;
    LocationManager locationManager;
    LocationListener locationListener;
    double currLat;
    double currLon;
    double targLat;
    double targLon;
    Location targetLocation = new Location("");
    TextView noch;
    TextView distance;
    Dialog detailsDialog;
    Dialog cancelDialog;
    Dialog finishDialog;
    String distanceInMeter;
    int intDistance;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_escort_start, container, false);
        //Zugriff auf die Komponenten in contentView
        Button detailsButton = contentView.findViewById(R.id.detailsButton);
        ImageView loadingAnimation = contentView.findViewById(R.id.imageView);
        AnimationDrawable animationDrawable = (AnimationDrawable) loadingAnimation.getBackground();
        animationDrawable.start();
        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override //öffnet Begleitpersonendetail-Dialog
            public void onClick(View view) {
                openDetailsDialog();
            }
        });
        //Position des Begleitungs-Items wird aus dem Bundle genommen
        Bundle bundle = getArguments();
        pos = bundle.getInt("position");
        escort = Server.user.getEscorts().get(pos);
        //Zielkoordinaten werden gespeichert
        targetLocation.setLatitude(Double.parseDouble(escort.getDestination().getLatitude()));
        targetLocation.setLongitude(Double.parseDouble(escort.getDestination().getLongitude()));
        targLat = Double.parseDouble(escort.getDestination().getLatitude());
        targLon = Double.parseDouble(escort.getDestination().getLongitude());
        // Standortermittlungs-Logik
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) { //Koordinaten werden im Hintergrund geupdated
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                currLat = latitude;
                currLon = longitude;
                Log.d("IN PROCESS","destination not yet reached");
                Log.d("DISTANCE", Float.toString(location.distanceTo(targetLocation)));
                intDistance = (int) Math.floor(location.distanceTo(targetLocation));
                distanceInMeter = Integer.toString(intDistance) + 'm'; //Distanz zum Ziel wird mitgeführt
                if(distance!=null) {
                    distance.setText(distanceInMeter); //und im Fahrtdetail-Dialog angezeigt
                    noch.setVisibility(View.VISIBLE);
                }
                if (location.distanceTo(targetLocation) <= 10) {
                    Log.d("GOAL","reached destination");
                    if(distance==null||distance.getText().equals(0)) //Fehlerabsicherung
                        return;
                    next();
                }
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {            }
            @Override
            public void onProviderEnabled(String provider) {            }
            @Override
            public void onProviderDisabled(String provider) {            }
        };
        // nach Standortermittlungs-Erlaubnis fragen
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        return contentView;
    }

    public void next() {
        //Begleitungs-Item wird aus der Escorts-Liste in die FinishedEscorts-Liste übertragen
        Server.user.getFinishedEscorts().add(escort);
        Server.user.getEscorts().remove(escort);
        //Position des Begleitungs-Items wird in eine Bundle für das nächste Fragment gegeben
        Bundle bundle = new Bundle();
        bundle.putInt("position",pos);
        EscortFinishFragment escortFinishFragment = new EscortFinishFragment();
        FragmentActivity activity = getActivity();
        //schließt alle offenen Dialoge bei Fragmentwechsel
        if (detailsDialog!=null)
            detailsDialog.dismiss();
        if (finishDialog!=null)
            finishDialog.dismiss();
        if (cancelDialog!=null)
            cancelDialog.dismiss();
        if (activity != null) {
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, escortFinishFragment).commit();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    public void openDetailsDialog() {
        //Dialogfenster: Begleitpersonendetails
        detailsDialog = new Dialog(getContext());
        detailsDialog.setContentView(R.layout.escort_dialogue_layout);
        detailsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView finishCloseButton = detailsDialog.findViewById(R.id.closeButton);
        Button finishButton = detailsDialog.findViewById(R.id.finish);
        Button cancelButton = detailsDialog.findViewById(R.id.cancel);
        ImageView closeButton = detailsDialog.findViewById(R.id.closeButton);
        TextView start = detailsDialog.findViewById(R.id.start_location);
        TextView start2 = detailsDialog.findViewById(R.id.start_location2);
        TextView destination = detailsDialog.findViewById(R.id.destination);
        TextView destination2 = detailsDialog.findViewById(R.id.destination2);
        TextView time = detailsDialog.findViewById(R.id.time);
        TextView service = detailsDialog.findViewById(R.id.service);
        TextView accompaniment = detailsDialog.findViewById(R.id.accompaniment);
        ImageView accompanimentPicture = detailsDialog.findViewById(R.id.accompanimentPicture);
        noch = detailsDialog.findViewById(R.id.noch);
        noch.setVisibility(View.GONE);
        if(distanceInMeter!=null) {
            noch.setVisibility(View.VISIBLE);
            distance = detailsDialog.findViewById(R.id.distance);
            distance.setText(distanceInMeter);
        }
        start.setText(escort.getStart().getAddressLine1());
        start2.setText(escort.getStart().getAddressLine2());
        destination.setText(escort.getDestination().getAddressLine1());
        destination2.setText(escort.getDestination().getAddressLine2());
        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("HH:mm");
        String t = Server.user.getEscortRequest().getTime().format(customFormat) + " Uhr";
        time.setText(t);
        service.setText(escort.getService().toString());
        accompaniment.setText(escort.getAccompaniment().toString());
        if(escort.getAccompaniment().getProfilepicture()!=null)
            accompanimentPicture.setImageDrawable(escort.getAccompaniment().getProfilepicture());
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsDialog.dismiss();
                openfinishDialog();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsDialog.dismiss();
                openCancelDialog();
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsDialog.dismiss();
            }
        });
        detailsDialog.show();
    }

    public void openCancelDialog(){
        //Dialogfenster: Stornierungsbestätigung
        cancelDialog = new Dialog(getContext());
        cancelDialog.setContentView(R.layout.cancel_dialogue_layout);
        cancelDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button yesButton = cancelDialog.findViewById(R.id.yesButton);
        Button noButton = cancelDialog.findViewById(R.id.noButton);
        ImageView closeButton = cancelDialog.findViewById(R.id.closeButton);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelDialog.dismiss();
                Server.user.getEscorts().remove(escort);
                getActivity().finish();
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelDialog.dismiss();
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelDialog.dismiss();
            }
        });
        cancelDialog.show();
    }
    public void openfinishDialog(){
        //Dialogfenster: Fahrtabschlussbestätigung
        finishDialog = new Dialog(getContext());
        finishDialog.setContentView(R.layout.finish_dialogue_layout);
        finishDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button yesButton = finishDialog.findViewById(R.id.yesButton);
        Button noButton = finishDialog.findViewById(R.id.noButton);
        ImageView closeButton = finishDialog.findViewById(R.id.closeButton);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishDialog.dismiss();
                next();
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishDialog.dismiss();
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishDialog.dismiss();
            }
        });
        finishDialog.show();
    }
}
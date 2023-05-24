package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SearchView;

public class EscortRateFragment extends Fragment {
    RatingBar ratingBar1;
    int appRating = 0;
    RatingBar ratingBar2;
    int accompRating = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_escort_rate, container, false);
        //Zugriff auf die Rating-Komponenten in contentView
        ratingBar1 = contentView.findViewById(R.id.ratingBar1);
        ratingBar1.setNumStars(5);
        appRating = ratingBar1.getNumStars();
        ratingBar2 = contentView.findViewById(R.id.ratingBar2);
        ratingBar2.setNumStars(5);
        accompRating = ratingBar2.getNumStars();
        Button finishButton = contentView.findViewById(R.id.finishButton);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        return contentView;
    }

    public void finish() { //temporaere Loesung
        appRating = ratingBar1.getNumStars();
        accompRating = ratingBar2.getNumStars();
        getActivity().finish();
    }
}
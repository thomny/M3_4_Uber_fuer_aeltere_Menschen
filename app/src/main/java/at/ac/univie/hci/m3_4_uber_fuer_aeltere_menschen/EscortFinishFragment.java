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
import android.widget.SearchView;

public class EscortFinishFragment extends Fragment {
    Escort escort;
    Dialog tipDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_escort_finish, container, false);
        //Dialogfenster: Trinkgeldoptionen
        tipDialog = new Dialog(getContext());
        tipDialog.setContentView(R.layout.tip_dialogue_layout);
        tipDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView tipCloseButton=tipDialog.findViewById(R.id.closeButton);
        Button tipButton1 = tipDialog.findViewById(R.id.button1);
        Button tipButton2 = tipDialog.findViewById(R.id.button2);
        Button tipButton3 = tipDialog.findViewById(R.id.button3);
        tipCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipDialog.dismiss();
            }
        });
        tipButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipDialog.dismiss();
            }
        });
        tipButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipDialog.dismiss();
            }
        });
        tipButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipDialog.dismiss();
            }
        });
        tipDialog.show();
        //Zugriff auf die Button-Komponenten in contentView
        Button rateButton = contentView.findViewById(R.id.rateButton);
        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rate();
            }
        });
        Button finishButton = contentView.findViewById(R.id.finishButton);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        return contentView;
    }

    public void finish() {
        getActivity().finish();
    }

    public void rate() { //temporaere Loesung -
        EscortRateFragment escortRateFragment = new EscortRateFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, escortRateFragment).commit();
    }
}
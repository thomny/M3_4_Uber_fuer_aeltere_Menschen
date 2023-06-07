package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RequestPaymentFragment extends Fragment {
    Dialog finishDialog;
    LinearLayout cashLayout;
    LinearLayout creditLayout;
    LinearLayout cardLayout;
    Button nextButton;
    Payment choice;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_request_payment, container, false);
        //Zugriff auf die Komponenten in contentView
        TextView cell1 = contentView.findViewById(R.id.cell1);
        cell1.setBackgroundResource(R.drawable.progress_bar_cell_filled);
        TextView cell2 = contentView.findViewById(R.id.cell2);
        cell2.setBackgroundResource(R.drawable.progress_bar_cell_filled);
        TextView cell3 = contentView.findViewById(R.id.cell3);
        cell3.setBackgroundResource(R.drawable.progress_bar_cell_filled);
        TextView cell4 = contentView.findViewById(R.id.cell4);
        cell4.setBackgroundResource(R.drawable.progress_bar_cell_filled);
        TextView cell5 = contentView.findViewById(R.id.cell5);
        cell5.setBackgroundResource(R.drawable.progress_bar_cell_filled);
        TextView cell6 = contentView.findViewById(R.id.cell6);
        cell6.setBackgroundResource(R.drawable.progress_bar_cell_filled);
        TextView amount = contentView.findViewById(R.id.amount);
        if(Server.user.getEscortRequest().getService()==Service.AUTO)
            amount.setText("Zu bezahlender Betrag: 15€");
        ImageButton backButton = contentView.findViewById(R.id.backButton);
        nextButton = contentView.findViewById(R.id.nextButton);
        cashLayout = contentView.findViewById(R.id.cashLayout);
        creditLayout = contentView.findViewById(R.id.creditLayout);
        cardLayout = contentView.findViewById(R.id.cardLayout);
        cashLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice = Payment.CASH;
                selectionLogic();
            }
        });
        creditLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice = Payment.CREDIT;
                selectionLogic();
            }
        });
        cardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice = Payment.CARD;
                selectionLogic();
            }
        });
        if(choice==null)
            nextButton.setBackgroundResource(R.drawable.grey_button);
        else nextButton.setBackgroundResource(R.drawable.button);
        // OnClickListener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(choice!=null)
                    next();
            }
        });
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                back();
            }
        });
        return contentView;
    }

    public void back() {
        RequestSummaryFragment requestSummaryFragment = new RequestSummaryFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,requestSummaryFragment).commit();
    }
    public void next() {
        //Daten werden in ein neues Escort-Objekt übernommen und in die Escort-Liste des Users eingefügt
        Escort finished_request = new Escort();
        finished_request.setPayment(choice);
        finished_request.setStart(Server.user.getEscortRequest().start);
        finished_request.setDestination(Server.user.getEscortRequest().destination);
        finished_request.setTime(Server.user.getEscortRequest().time);
        finished_request.setService(Server.user.getEscortRequest().service);
        finished_request.setAccompaniment(Server.user.getEscortRequest().accompaniment);
        finished_request.setId();
        Server.user.getEscorts().add(finished_request);
        Server.accept(finished_request); //Mock: ausgewählte Begleitperson bestätigt die Anfrage
        //Dialogfenster: Abschluss einer erfolgreichen Anfrage
        finishDialog = new Dialog(getContext());
        finishDialog.setContentView(R.layout.request_dialogue_layout);
        finishDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView finishCloseButton = finishDialog.findViewById(R.id.closeButton);
        Button tipButton1 = finishDialog.findViewById(R.id.button1);
        //schließt Dialogfenster bei Betätigung der Schaltfläche
        tipButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishDialog.dismiss();
                finish();
            }
        });
        //schließt Dialogfenster bei Betätigung des x-Symbols
        finishCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishDialog.dismiss();
                finish();
            }
        });
        //beendet die Activity by Schließen des Dialogfensters
        finishDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                finish();
            }
        });
        finishDialog.show();
    }

    public void finish (){
        //ausgewählte Daten werden bei Abbruch der Anforderung gelöscht
        Server.user.getEscortRequest().setTime(false);
        Server.user.getEscortRequest().setStart(null);
        Server.user.getEscortRequest().setDestination(null);
        Server.user.getEscortRequest().setService(null);
        Server.user.getEscortRequest().setAccompaniment(null);
        getActivity().finish();
    }
    public void selectionLogic(){ //Zahlungsoptionen-Logik
        nextButton.setBackgroundResource(R.drawable.button);
        if(choice.equals(Payment.CASH))
            cashLayout.setBackgroundResource(R.drawable.selected_item_border);
        else cashLayout.setBackgroundResource(R.drawable.unselected_item_border);
        if(choice.equals(Payment.CREDIT))
            creditLayout.setBackgroundResource(R.drawable.selected_item_border);
        else creditLayout.setBackgroundResource(R.drawable.unselected_item_border);
        if(choice.equals(Payment.CARD))
            cardLayout.setBackgroundResource(R.drawable.selected_item_border);
        else cardLayout.setBackgroundResource(R.drawable.unselected_item_border);
    }
}
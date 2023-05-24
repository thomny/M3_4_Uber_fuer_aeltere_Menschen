package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.time.format.DateTimeFormatter;

public class RequestAccompanimentListFragment extends Fragment {
    Dialog detailsDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_request_accompaniment_list, container, false);
        //Zugriff auf die Komponenten in contentView
        TextView cell1 = contentView.findViewById(R.id.cell1);
        cell1.setBackgroundResource(R.drawable.progress_bar_cell_filled);
        TextView cell2 = contentView.findViewById(R.id.cell2);
        cell2.setBackgroundResource(R.drawable.progress_bar_cell_filled);
        TextView cell3 = contentView.findViewById(R.id.cell3);
        cell3.setBackgroundResource(R.drawable.progress_bar_cell_filled);
        TextView cell4 = contentView.findViewById(R.id.cell4);
        cell4.setBackgroundResource(R.drawable.progress_bar_cell_filled);
        ImageButton backButton = contentView.findViewById(R.id.backButton);
        ListView accompanimentsList = contentView.findViewById(R.id.accompanimentsList);
        TextView listStatus = contentView.findViewById(R.id.listStatus);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        if(!Server.accompaniments.isEmpty())
            listStatus.setText("");
        //Adapter fuer die Begleitpersonen wird erstellt und festgelegt
        AccompanimentAdapter accompanimentAdapter = new AccompanimentAdapter(getContext(), Server.accompaniments);
        accompanimentsList.setAdapter(accompanimentAdapter);
        accompanimentsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override //Auswahl einer Begleitperson führt zum Begleitungsdeteil-Screen der jeweiligen Begleitperson
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Accompaniment accompaniment = (Accompaniment) adapterView.getItemAtPosition(i);
                if(accompaniment==null)
                    return;
                openDetailsDialog(accompaniment);
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
    public void back() { //Zurueck-Icon fuehrt zur vorherigen Activity zurueck
        RequestServiceFragment requestServiceFragment = new RequestServiceFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,requestServiceFragment).commit();
    }

    public void openDetailsDialog(Accompaniment accompaniment) {
        //Dialogfenster: Abschluss einer erfolgreichen Anfrage
        detailsDialog = new Dialog(getContext());
        detailsDialog.setContentView(R.layout.accompaniment_dialogue_layout);
        detailsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView accompanimentPicture = detailsDialog.findViewById(R.id.accompanimentPicture);
        ImageView closeButton = detailsDialog.findViewById(R.id.closeButton);
        Button chooseButton = detailsDialog.findViewById(R.id.choose);
        TextView accompanimentName = detailsDialog.findViewById(R.id.accompanimentName);
        TextView accompanimentAge = detailsDialog.findViewById(R.id.accompanimentAge);
        TextView accompanimentOccupation = detailsDialog.findViewById(R.id.accompanimentOccupation);
        TextView accompanimentDescription = detailsDialog.findViewById(R.id.accompanimentDescription);
        accompanimentName.setText(accompaniment.getName());
        accompanimentAge.setText(Integer.toString(accompaniment.getAge()));
        accompanimentOccupation.setText(accompaniment.getOccupation());
        accompanimentDescription.setText(accompaniment.getDescription());
        if(Server.user.getProfilepicture()!=null)
            accompanimentPicture.setImageDrawable(accompaniment.getProfilepicture());
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsDialog.dismiss();
            }
        });
        chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Server.user.getEscortRequest().setAccompaniment(accompaniment);
                detailsDialog.dismiss();
                RequestSummaryFragment requestSummaryFragment = new RequestSummaryFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,requestSummaryFragment).commit();
            }
        });
        detailsDialog.show();
    }
}
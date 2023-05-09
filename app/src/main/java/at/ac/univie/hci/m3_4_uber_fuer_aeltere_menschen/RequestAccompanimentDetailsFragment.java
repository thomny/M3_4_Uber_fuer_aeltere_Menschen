package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

public class RequestAccompanimentDetailsFragment extends Fragment {
    Accompaniment accompaniment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_request_accompaniment_details, container, false);

        Bundle bundle = getArguments();
        accompaniment = (Accompaniment) bundle.getParcelable("accompaniment");

        ImageButton backButton = contentView.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        Button nextButton = contentView.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });
        TextView accompanimentName = contentView.findViewById(R.id.accompanimentName);
        TextView accompanimentAge = contentView.findViewById(R.id.accompanimentAge);
        TextView accompanimentOccupation = contentView.findViewById(R.id.accompanimentOccupation);
        TextView accompanimentDescription = contentView.findViewById(R.id.accompanimentDescription);
        accompanimentName.setText(accompaniment.getName());
        accompanimentAge.setText(Integer.toString(accompaniment.getAge()));
        accompanimentOccupation.setText(accompaniment.getOccupation());
        accompanimentDescription.setText(accompaniment.getDescription());
        return contentView;
    }

    public void back() { //Zurueck-Icon fuehrt zur vorherigen Activity zurueck
        RequestAccompanimentListFragment requestAccompanimentListFragment = new RequestAccompanimentListFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,requestAccompanimentListFragment).commit();
    }
    public void next() { //temporaere Loesung
        User.escort_request.setAccompaniment(accompaniment);
        RequestSummaryFragment requestSummaryFragment = new RequestSummaryFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,requestSummaryFragment).commit();
    }
}
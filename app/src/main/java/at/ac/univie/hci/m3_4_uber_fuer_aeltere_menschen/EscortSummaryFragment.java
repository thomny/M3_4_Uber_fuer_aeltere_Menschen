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

public class EscortSummaryFragment extends Fragment {
    Escort escort;
    Integer pos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_escort_summary, container, false);
        Button nextButton = contentView.findViewById(R.id.nextButton);
        Bundle bundle = getArguments();
        pos = bundle.getInt("position");
        escort = User.escorts.get(pos);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });

        return contentView;
    }

    public void next() { //temporaere Loesung -
        if(escort==null) {
            Button nextButton = getView().findViewById(R.id.nextButton);
            nextButton.setText(pos.toString());
            return;
        }
        escort.setUserReady();
        if(escort.getUserReady()&&escort.getAccompReady()) {
            Bundle bundle = new Bundle();
            bundle.putInt("position",pos);
            EscortStartFragment escortStartFragment = new EscortStartFragment();
            escortStartFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, escortStartFragment).commit();
        }
    }
}
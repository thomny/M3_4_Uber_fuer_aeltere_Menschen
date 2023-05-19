package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class AccountFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_account, container, false);
        Button changeButton = contentView.findViewById(R.id.changeButton);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return;
            }
        });
        TextView userName = contentView.findViewById(R.id.userName);
        TextView userAge = contentView.findViewById(R.id.userAge);
        TextView userOccupation = contentView.findViewById(R.id.userOccupation);
        TextView userDescription = contentView.findViewById(R.id.userDescription);
        userName.setText(User.username);
        userAge.setText(Integer.toString(User.age));
        userOccupation.setText(User.occupation);
        userDescription.setText(User.description);
        return contentView;
    }
}
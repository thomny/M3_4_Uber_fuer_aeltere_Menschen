package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

public class AccountFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_account, container, false);
        Button changeButton = contentView.findViewById(R.id.changeButton);
        changeButton.setVisibility(View.GONE); //aus zeitlichen Gründen nicht implementierbar
        //Topleiste-Menü
        ImageButton menu = contentView.findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu(view);
            }
        });
        //Anzeige des eigenen Profils
        TextView userName = contentView.findViewById(R.id.userName);
        TextView userAge = contentView.findViewById(R.id.userAge);
        TextView userOccupation = contentView.findViewById(R.id.userOccupation);
        TextView userDescription = contentView.findViewById(R.id.userDescription);
        ImageView picture = contentView.findViewById(R.id.userPicture);
        if(Server.user.getProfilepicture()!=null)
            picture.setImageDrawable(Server.user.getProfilepicture());
        userName.setText(Server.user.getUserName());
        if(Server.user.getAge()==0)
            userAge.setText("k.A.");
        else userAge.setText(Integer.toString(Server.user.getAge()) + " Jahre alt");
        if(Server.user.getOccupation()==null)
            userOccupation.setText("k.A.");
        else userOccupation.setText(Server.user.getOccupation());
        if(Server.user.getDescription()==null)
            userDescription.setText("k.A.");
        else userDescription.setText(Server.user.getDescription());
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {            }
        });
        return contentView;
    }

    public void popupMenu(View view) { //TopBar-Menü
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.topbarmenu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuItem2: logout(); //Abmeldeoption
                        return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    public void logout() { //Abmelden
        Server.user = null;
        getActivity().finish();
    }
}
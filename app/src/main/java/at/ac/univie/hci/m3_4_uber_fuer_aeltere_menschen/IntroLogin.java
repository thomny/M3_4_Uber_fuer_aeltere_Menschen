package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

public class IntroLogin extends Fragment {
    String email;
    String password;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_intro_login, container, false);
        //Zugriff auf die Komponenten in contentView
        EditText accountNameEditText = contentView.findViewById(R.id.accountname);
        EditText passwordEditText = contentView.findViewById(R.id.passwordView);
        Button loginButton = contentView.findViewById(R.id.loginButton);
        ImageButton backButton = contentView.findViewById(R.id.backButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = accountNameEditText.getText().toString();
                password = passwordEditText.getText().toString();
                next();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
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

    public void next(){ //Login-Logik
        if(email.isEmpty()&&password.isEmpty()) {
            Toast.makeText(getContext(), "Email und Passwort ist leer.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(email.isEmpty()) {
            Toast.makeText(getContext(), "Email ist leer.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.isEmpty()) {
            Toast.makeText(getContext(), "Passwort ist leer.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Server.userList.containsKey(email)){
            User user;
            user = (User) Server.userList.get(email);
            if(user.getPassword().equals(password)){
                Server.user = (User) Server.userList.get(email);
                Intent main = new Intent(getContext(), MainActivity.class);
                startActivity(main);
                back();
            }
        } else Toast.makeText(getContext(), "Email oder Passwort falsch.", Toast.LENGTH_SHORT).show();
    }

    public void back() {
        IntroWelcome introWelcomeFragment = new IntroWelcome();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,introWelcomeFragment).commit();
    }
}
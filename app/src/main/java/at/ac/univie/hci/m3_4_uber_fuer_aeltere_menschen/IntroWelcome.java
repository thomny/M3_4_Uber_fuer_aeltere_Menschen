package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.content.Intent;
import android.media.Image;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

public class IntroWelcome extends Fragment {
    int dummyNumber = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_intro_welcome, container, false);
        //Zugriff auf die Komponenten in contentView
        ImageButton menu = contentView.findViewById(R.id.menu);
        menu.setVisibility(View.GONE);
        Button registerButton = contentView.findViewById(R.id.registerButton);
        Button loginButton = contentView.findViewById(R.id.loginButton);
        ImageView logo = contentView.findViewById(R.id.logo);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dummyNumber == 2) {
                    dummyNumber = 0;
                    dummyLogin();
                } else login();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ++dummyNumber;
            }
        });
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {            }
        });
        return contentView;
    }

    public void login() { //führt zum LoginScreen
        IntroLogin introLoginFragment = new IntroLogin();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,introLoginFragment).commit();
    }
    public void register() { //führt zum RegistrierungsScreen
        IntroRegister introRegisterFragment = new IntroRegister();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,introRegisterFragment).commit();
    }

    public void dummyLogin() { //schneller Einstieg in die App für Testzwecke
        Server.user = (User) Server.userList.get("dummy@mail.at");
        Toast.makeText(getContext(), "Anmeldung als Testuser erfolgreich", Toast.LENGTH_SHORT).show();
        Intent main = new Intent(getContext(), MainActivity.class);
        startActivity(main);
    }
}
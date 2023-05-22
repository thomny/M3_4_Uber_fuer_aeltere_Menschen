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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class IntroWelcome extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_intro_welcome, container, false);

        TextView textView4 = contentView.findViewById(R.id.textView4);
        textView4.setText(Html.fromHtml("<u>" + "So funktioniert’s!" + "</u>"));
        TextView registerButton = contentView.findViewById(R.id.registerTextView);
        registerButton.setText(Html.fromHtml("oder " + "<u>" + "registrieren" + "</u>"));

        Button loginButton = contentView.findViewById(R.id.loginButton);
        ImageView logo = contentView.findViewById(R.id.logo);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
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
                dummyLogin();
            }
        });
        return contentView;
    }

    public void login() {
        IntroLogin introLoginFragment = new IntroLogin();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,introLoginFragment).commit();
    }
    public void register() {
        IntroRegister introRegisterFragment = new IntroRegister();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,introRegisterFragment).commit();
    }

    public void dummyLogin() {
        Server.user = (User) Server.userList.get("dummy@mail.at");
        Intent main = new Intent(getContext(), MainActivity.class);
        startActivity(main);
    }
}
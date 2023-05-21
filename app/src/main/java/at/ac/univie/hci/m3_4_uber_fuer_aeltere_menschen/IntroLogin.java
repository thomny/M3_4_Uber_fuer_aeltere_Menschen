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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class IntroLogin extends Fragment {
    String accountName;
    String password;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_intro_login, container, false);

        EditText accountNameEditText = contentView.findViewById(R.id.accountname);
        EditText passwordEditText = contentView.findViewById(R.id.passwordView);
        Button loginButton = contentView.findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountName = accountNameEditText.getText().toString();
                password = passwordEditText.getText().toString();
                next();
            }
        });
        return contentView;
    }

    public void next(){
        if(accountName.isEmpty()&&password.isEmpty()) {
            Toast.makeText(getContext(), "Benutzername und Passwort ist leer.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(accountName.isEmpty()) {
            Toast.makeText(getContext(), "Benutzername ist leer.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.isEmpty()) {
            Toast.makeText(getContext(), "Passwort ist leer.", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User(accountName,password);
        if(!Server.userList.get(user.getUserName()).equals(user)){
            user = (User) Server.userList.get(user.getUserName());
            if(user.getPassword().equals(password)){
                Server.user = (User) Server.userList.get(user.getUserName());
                Intent main = new Intent(getContext(), MainActivity.class);
                startActivity(main);
            } else Toast.makeText(getContext(), "Passwort ist falsch.", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(getContext(), "Benutzer nicht vorhanden.", Toast.LENGTH_SHORT).show();
    }
}
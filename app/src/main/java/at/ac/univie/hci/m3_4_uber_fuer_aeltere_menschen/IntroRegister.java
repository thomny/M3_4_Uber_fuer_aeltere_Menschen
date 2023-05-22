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

import androidx.fragment.app.Fragment;

public class IntroRegister extends Fragment {
    String name;
    String email;
    String password;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_intro_register, container, false);

        EditText nameEditText = contentView.findViewById(R.id.name);
        EditText accountNameEditText = contentView.findViewById(R.id.accountname);
        EditText passwordEditText = contentView.findViewById(R.id.passwordView);
        Button loginButton = contentView.findViewById(R.id.loginButton);
        ImageButton backButton = contentView.findViewById(R.id.backButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lowercaseName = nameEditText.getText().toString().toLowerCase();
                name = lowercaseName.substring(0, 1).toUpperCase() + lowercaseName.substring(1);
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
        return contentView;
    }

    public void next(){
        if(email.isEmpty()||password.isEmpty()||name.isEmpty()) {
            Toast.makeText(getContext(), "Bitte füllen Sie alle Felder aus.", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User(name,email,password);
        if(!Server.userList.containsKey(email)) {
            Server.userList.put(email,user);
            Server.user = user;
            Intent main = new Intent(getContext(), MainActivity.class);
            startActivity(main);
        } else Toast.makeText(getContext(), "Email bereits registriert", Toast.LENGTH_SHORT).show();
    }

    public void back() {
        IntroWelcome introWelcomeFragment = new IntroWelcome();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,introWelcomeFragment).commit();
    }
}
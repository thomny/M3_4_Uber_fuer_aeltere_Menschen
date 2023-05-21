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
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class IntroWelcome extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_intro_welcome, container, false);

        TextView textView4 = contentView.findViewById(R.id.textView3);
        textView4.setText(Html.fromHtml("<u>" + "Sie wollen eine Begleitung anbieten?" + "</u>"));
        TextView registerButton = contentView.findViewById(R.id.registerTextView);
        textView4.setText(Html.fromHtml("<u>" + "Sie wollen eine Begleitung anbieten?" + "</u>"));

        Button loginButton = contentView.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return;
            }
        });
        return contentView;
    }

    public void next() {
        IntroLogin introLoginFragment = new IntroLogin();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,introLoginFragment).commit();
    }
}
package edu.temple.simplewebbrowser;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements WebPageFragment.BrowserListener {

    EditText addressBar;
    Button goButton;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addressBar = findViewById(R.id.addressBar);
        goButton = findViewById(R.id.goButton);
        fragmentManager = getSupportFragmentManager();

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                String webAddress = addressBar.getText().toString();
                WebPageFragment webPageFragment = WebPageFragment.newInstance(webAddress);
                fragmentTransaction.replace(R.id.webPageContainer, webPageFragment).addToBackStack(null).commit();
            }
        });
    }
}

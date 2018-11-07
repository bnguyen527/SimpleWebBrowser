package edu.temple.simplewebbrowser;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity implements TabFragment.BrowserListener {

    EditText addressBar;
    Button goButton;
    FragmentManager fragmentManager;
    TabFragment browserTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addressBar = findViewById(R.id.addressBar);
        goButton = findViewById(R.id.goButton);
        fragmentManager = getSupportFragmentManager();
        browserTab = new TabFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.webPageContainer, browserTab).commit();

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String webAddress = addressBar.getText().toString();
                browserTab.loadUrl(webAddress);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.browser_menu, menu);
        return true;
    }

    @Override
    public void setAddressBar(String webAddress) {
        addressBar.setText(webAddress);
    }
}

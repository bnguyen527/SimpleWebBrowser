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

public class MainActivity extends AppCompatActivity implements WebPageFragment.BrowserListener {

    EditText addressBar;
    Button goButton;
    FragmentManager fragmentManager;
    ArrayList<WebPageFragment> browsingHistory;
    ListIterator<WebPageFragment> historyIterator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addressBar = findViewById(R.id.addressBar);
        goButton = findViewById(R.id.goButton);
        fragmentManager = getSupportFragmentManager();
        browsingHistory = new ArrayList<>();
        historyIterator = browsingHistory.listIterator();

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String webAddress = addressBar.getText().toString();
                WebPageFragment webPageFragment = WebPageFragment.newInstance(webAddress);
                historyIterator.add(webPageFragment);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.webPageContainer, webPageFragment).commit();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.browser_menu, menu);
        return true;
    }

    @Override
    public void goBack() {
        if (!browsingHistory.isEmpty()) {
            historyIterator.previous();
            if (historyIterator.hasPrevious()) {
                WebPageFragment previousPage = historyIterator.previous();
                historyIterator.next();
                addressBar.setText(previousPage.getWebAddress());
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.webPageContainer, previousPage).commit();
            } else {
                historyIterator.next();
            }
        }
    }

    @Override
    public void goForward() {
        if (historyIterator.hasNext()) {
            WebPageFragment nextPage = historyIterator.next();
            addressBar.setText(nextPage.getWebAddress());
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.webPageContainer, nextPage).commit();
        }
    }
}

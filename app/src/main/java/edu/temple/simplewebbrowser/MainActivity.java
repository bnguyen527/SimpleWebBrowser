package edu.temple.simplewebbrowser;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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
    ViewPager tabManager;
    FragmentManager fragmentManager;
    ArrayList<TabFragment> browserTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addressBar = findViewById(R.id.addressBar);
        goButton = findViewById(R.id.goButton);
        tabManager = findViewById(R.id.tabManager);
        fragmentManager = getSupportFragmentManager();
        browserTabs = new ArrayList<>();
        browserTabs.add(new TabFragment());
        tabManager.setAdapter(new TabFragmentAdapter(fragmentManager, browserTabs));

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String webAddress = addressBar.getText().toString();
                browserTabs.get(tabManager.getCurrentItem()).loadUrl(webAddress);
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

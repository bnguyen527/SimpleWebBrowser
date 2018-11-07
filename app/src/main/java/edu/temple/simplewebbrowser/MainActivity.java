package edu.temple.simplewebbrowser;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity implements TabFragment.BrowserListener {

    EditText addressBar;
    Button goButton;
    ViewPager tabManager;
    TabFragmentAdapter tabFragmentAdapter;
    FragmentManager fragmentManager;
    ArrayList<TabFragment> browserTabs;
    ListIterator<TabFragment> tabIterator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addressBar = findViewById(R.id.addressBar);
        goButton = findViewById(R.id.goButton);
        tabManager = findViewById(R.id.tabManager);
        fragmentManager = getSupportFragmentManager();
        browserTabs = new ArrayList<>();
        tabIterator = browserTabs.listIterator();
        tabIterator.add(new TabFragment());
        tabFragmentAdapter = new TabFragmentAdapter(fragmentManager, browserTabs);
        tabManager.setAdapter(tabFragmentAdapter);

        tabManager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageSelected(int i) {
                addressBar.setText(browserTabs.get(i).getCurrentAddress());
            }

            @Override
            public void onPageScrollStateChanged(int i) { }
        });

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newButton:
                tabIterator.add(new TabFragment());
                tabFragmentAdapter.notifyDataSetChanged();
                tabManager.setCurrentItem(tabManager.getCurrentItem() + 1);
                return true;
            case R.id.previousButton:
                if (!browserTabs.isEmpty()) {
                    tabIterator.previous();
                    if (tabIterator.hasPrevious()) {
                        tabManager.setCurrentItem(tabManager.getCurrentItem() - 1);
                    } else {
                        tabIterator.next();
                    }
                }
                return true;
            case R.id.nextButton:
                if (tabIterator.hasNext()) {
                    tabIterator.next();
                    tabManager.setCurrentItem(tabManager.getCurrentItem() + 1);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setAddressBar(String webAddress) {
        addressBar.setText(webAddress);
    }
}

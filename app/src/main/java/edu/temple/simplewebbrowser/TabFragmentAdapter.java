package edu.temple.simplewebbrowser;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

class TabFragmentAdapter extends FragmentStatePagerAdapter {

    private ArrayList<TabFragment> browserTabs;

    TabFragmentAdapter(FragmentManager fm, ArrayList<TabFragment> browserTabs) {
        super(fm);
        this.browserTabs = browserTabs;
    }

    @Override
    public Fragment getItem(int i) {
        return browserTabs.get(i);
    }

    @Override
    public int getCount() {
        return browserTabs.size();
    }
}

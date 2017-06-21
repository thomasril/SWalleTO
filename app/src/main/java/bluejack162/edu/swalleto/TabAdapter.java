package bluejack162.edu.swalleto;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Thomas Asril on 6/21/2017.
 */

public class TabAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> listFragment;
    ArrayList<String> listTab;
    Context context;

    public TabAdapter(FragmentManager fm) {
        super(fm);
        listFragment = new ArrayList<>();
        listTab = new ArrayList<>();
    }

    public void add (Fragment fragment, String tab) {
        listFragment.add(fragment);
        listTab.add(tab);
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listTab.get(position);
    }
}

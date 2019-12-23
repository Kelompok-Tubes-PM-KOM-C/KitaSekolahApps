package com.example.kita_sekolah;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class DonasiAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentsList = new ArrayList<>();
    private List<String> FragmentListTitles = new ArrayList<>();

    public DonasiAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);
    }

    @Override
    public int getCount() {
        return FragmentListTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
       return FragmentListTitles.get(position);
    }

    public void addFragment(Fragment fragment, String Title){
        fragmentsList.add(fragment);
        FragmentListTitles.add(Title);
    }
}

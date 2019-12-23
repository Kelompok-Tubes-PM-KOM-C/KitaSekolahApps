package com.example.kita_sekolah;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

public class AccountFragment extends Fragment {
    View myFragment;

    ViewPager viewPager;
    TabLayout tabLayout;

    public AccountFragment() {

    }

    public static AccountFragment getInstance() {
        return new AccountFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_account, container, false);

        tabLayout = myFragment.findViewById(R.id.acc_tablayout);
        viewPager = myFragment.findViewById(R.id.acc_viewpager);

        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager(ViewPager viewPager) {
        ProfileAdapter adapter = new ProfileAdapter(getChildFragmentManager());

        adapter.addFragment(new ListDonasiFragment(), "Donasiku");
        adapter.addFragment(new ListPesananFragment(), "Pesananku");
        adapter.addFragment(new DataDiriFragment(), "Data Diriku");

        viewPager.setAdapter(adapter);
    }
}


package com.example.kita_sekolah;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;

public class AccountFragment extends Fragment {
    View myFragment;

    ViewPager viewPager;
    TabLayout tabLayout;

    Button btnLogout = null;
    Intent intent;
    FirebaseAuth mFirebaseAuth;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

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

        intent = new Intent(getActivity(), login.class);
        btnLogout = myFragment.findViewById(R.id.btn_logout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Paper.book().destroy();
                startActivity(intent);
            }
        });


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


package com.example.jingnan.assignment2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by JINGNAN on 2016-01-23.
 */
public class MainFragment extends Fragment implements View.OnClickListener{

    View view;
    public Button buttonEnterNames, buttonStore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_fragment, container, false);
        buttonStore = (Button) view.findViewById(R.id.store);
        buttonStore.setOnClickListener(this);
        buttonEnterNames = (Button) view.findViewById(R.id.enterNames);
        buttonEnterNames.setOnClickListener(this);
        //buttonStore = (Button) view.findViewById(R.id.store);
        //buttonStore.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        switch (v.getId()) {
            case R.id.enterNames:
                EnterNameFragment enterNameFragment = new EnterNameFragment();
                fragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.frameLayoutFragmentContainer, enterNameFragment)
                        .commit();
                break;
            case R.id.store:
                StoreFragment storeFragment = new StoreFragment();
                fragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.frameLayoutFragmentContainer, storeFragment)
                        .commit();
                break;
        }

    }
}

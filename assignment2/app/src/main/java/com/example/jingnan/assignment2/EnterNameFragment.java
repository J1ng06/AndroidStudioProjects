package com.example.jingnan.assignment2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by JINGNAN on 2016-01-20.
 */
public class EnterNameFragment extends Fragment {
    public static void EnterName(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.enterName_fragment, container, false);
    }
}

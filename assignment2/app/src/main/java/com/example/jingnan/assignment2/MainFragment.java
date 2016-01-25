package com.example.jingnan.assignment2;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by JINGNAN on 2016-01-23.
 */
public class MainFragment extends Fragment implements View.OnClickListener{

    View view;
    public Button buttonEnterNames, buttonStore, btuttonLoad, buttonView, buttonExit;
    onClickedListnener callbackCall;

    public interface onClickedListnener {
        public void ViewBtnClicked();
        public void ExitBtnClicked();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callbackCall = (onClickedListnener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onClickListener");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_fragment, container, false);
        buttonStore = (Button) view.findViewById(R.id.store);
        buttonStore.setOnClickListener(this);
        buttonEnterNames = (Button) view.findViewById(R.id.enterNames);
        buttonEnterNames.setOnClickListener(this);
        btuttonLoad = (Button) view.findViewById(R.id.load);
        btuttonLoad.setOnClickListener(this);
        buttonView = (Button) view.findViewById(R.id.view);
        buttonView.setOnClickListener(this);
        buttonExit = (Button) view.findViewById(R.id.exit);
        buttonExit.setOnClickListener(this);
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
            case R.id.load:
                LoadFragment loadFragment = new LoadFragment();
                fragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.frameLayoutFragmentContainer, loadFragment)
                        .commit();
                break;
            case R.id.view:
                callbackCall.ViewBtnClicked();
                break;
            case R.id.exit:
                callbackCall.ExitBtnClicked();
                break;
        }

    }
}

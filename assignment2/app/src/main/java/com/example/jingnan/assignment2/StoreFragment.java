package com.example.jingnan.assignment2;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by JINGNAN on 2016-01-24.
 */
public class StoreFragment extends Fragment{
    public Button btnStore;
    StoreListener callbackCall;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.store_fragment, container, false);
        btnStore = (Button) view.findViewById(R.id.btnStore);
        btnStore.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                btnStoreClicked(v);
            }
        });

        return view;
    }

    public interface StoreListener{
        //public
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callbackCall = (StoreListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DoneListener");
        }
    }

    public void btnStoreClicked(View v){
        
    }
}

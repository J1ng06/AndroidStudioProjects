package com.example.jingnan.assignment2;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by JINGNAN on 2016-01-24.
 */
public class StoreFragment extends Fragment{
    public Button btnStore;
    public EditText fileNameField;
    public String filename_text;
    StoreListener callbackCall;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.store_fragment, container, false);
        fileNameField = (EditText)view.findViewById(R.id.fileNameField);
        btnStore = (Button) view.findViewById(R.id.btnStore);
        btnStore.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                btnStoreClicked(v);
            }
        });

        return view;
    }

    public interface StoreListener{
        public void onClickStoreBtn(String filename);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callbackCall = (StoreListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement StoreListener");
        }
    }

    public void btnStoreClicked(View v){
        filename_text = String.valueOf(fileNameField.getText());
        if (TextUtils.isEmpty(filename_text)) {
            Toast.makeText(getActivity(), "Please Enter Filename",
                    Toast.LENGTH_SHORT).show();
        }else{
            callbackCall.onClickStoreBtn(filename_text);
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }
}

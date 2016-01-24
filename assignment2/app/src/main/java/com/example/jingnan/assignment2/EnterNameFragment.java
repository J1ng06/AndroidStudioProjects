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
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by JINGNAN on 2016-01-20.
 */
public class EnterNameFragment extends Fragment {
    public EditText name_text, age_text;
    public Button btnAdd, btnDone;
    public Spinner movieSpinner;
    private String ppl_info;
    private ArrayList<String> name_list = new ArrayList<>();
    DoneListener callbackCall;
    public interface DoneListener {
        public void onClickDoneBtn(ArrayList arrayList);
        public ArrayList<String> getNameList();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callbackCall = (DoneListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DoneListener");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.enter_name_fragment, container, false);

        name_text = (EditText) view.findViewById(R.id.name);
        age_text = (EditText) view.findViewById(R.id.age);
        movieSpinner = (Spinner)view.findViewById(R.id.movieSpinner);
        btnAdd = (Button) view.findViewById(R.id.add);
        btnDone = (Button) view.findViewById(R.id.done);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btnAddClicked(v);
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                btnDoneClicked(v);
            }
        });
        return view;
    }

    public void btnAddClicked(View v){
        String name, age;
        name = String.valueOf(name_text.getText());
        age = String.valueOf(age_text.getText());

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(age)) {
            Toast.makeText(getActivity(), "Please Enter Both Name and Age",
                    Toast.LENGTH_SHORT).show();
        }else{
            ppl_info = name + " - " + age + " - " + movieSpinner.getSelectedItem().toString();
            name_list = callbackCall.getNameList();
            name_list.add(ppl_info);
            Toast.makeText(getActivity(), "'"+ ppl_info + "'" + "has been added.",
                    Toast.LENGTH_SHORT).show();
            name_text.setText("");
            age_text.setText("");
        }
    };

    public void btnDoneClicked(View v){
        if (name_list != null) {
        callbackCall.onClickDoneBtn(name_list);}
        getActivity().getSupportFragmentManager().popBackStack();
    };

}

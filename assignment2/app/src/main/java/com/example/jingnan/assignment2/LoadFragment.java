package com.example.jingnan.assignment2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by JINGNAN on 2016-01-24.
 */
public class LoadFragment extends Fragment{
    public ListView file_list_view, fileContent;
    public TextView fileLabel;
    LoadListener callbackCall;
    public String[] file_content;

    public interface LoadListener {
        public void onClickLoadBtn(ListView list_view);
        public String FileContent(String filename, Context context);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callbackCall = (LoadListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement LoadListener");
        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.load_fragment, container, false);
        file_list_view = (ListView) view.findViewById(R.id.fileList);
        fileContent = (ListView) view.findViewById(R.id.fileContent);
        fileLabel = (TextView) view.findViewById(R.id.fileTitleLabel);
        callbackCall.onClickLoadBtn(file_list_view);
        file_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                String item = ((TextView)view).getText().toString();
                file_content = callbackCall.FileContent(item, getActivity().getApplicationContext()).split("/n");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                        R.layout.simple_list_item2, android.R.id.text2, file_content);
                fileContent.setAdapter(adapter);
                fileLabel.setText("File Content for " + item);
            }
        });

        return view;
    }
}

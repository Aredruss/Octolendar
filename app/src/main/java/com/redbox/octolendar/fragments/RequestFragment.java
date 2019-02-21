package com.redbox.octolendar.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.redbox.octolendar.R;

public class RequestFragment extends Fragment {

    private TextView requestTextView;
    private Button requestButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.request_fragment, container,false);

        requestButton = view.findViewById(R.id.boredButton);
        requestTextView = view.findViewById(R.id.boredTextView);

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getContext(), "Clicked", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        return view;

    }
}

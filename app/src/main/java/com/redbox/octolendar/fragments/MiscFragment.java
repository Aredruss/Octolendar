package com.redbox.octolendar.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.redbox.octolendar.R;

public class MiscFragment extends Fragment {

    private TextView githubTexView;
    private TextView feedbackTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View miscView = inflater.inflate(R.layout.misc_fragment_layout, container, false);


        githubTexView = miscView.findViewById(R.id.githubTextView);
        feedbackTextView = miscView.findViewById(R.id.feedbackTextView);

        githubTexView.setMovementMethod(LinkMovementMethod.getInstance());
        feedbackTextView.setMovementMethod(LinkMovementMethod.getInstance());

        return miscView;
    }
}

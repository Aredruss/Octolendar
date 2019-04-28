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
import com.redbox.octolendar.singleton.App;

import java.util.List;

public class MiscFragment extends Fragment {

    private TextView githubTexView;
    private TextView feedbackTextView;
    private App.EventDatabase database;
    private App.EventDao dao;
    private int count;
    private int completed;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View miscView = inflater.inflate(R.layout.misc_fragment, container, false);

        database = App.getInstance().getEventDatabase();
        dao = database.eventDao();

        List<App.Event> events = dao.getAll();
        count = events.size();



        githubTexView = miscView.findViewById(R.id.githubTextView);
        feedbackTextView = miscView.findViewById(R.id.feedbackTextView);

        githubTexView.setMovementMethod(LinkMovementMethod.getInstance());
        feedbackTextView.setMovementMethod(LinkMovementMethod.getInstance());

        return miscView;
    }
}

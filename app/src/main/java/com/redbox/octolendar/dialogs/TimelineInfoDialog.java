package com.redbox.octolendar.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.redbox.octolendar.R;

public class TimelineInfoDialog extends android.support.v4.app.DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.timeline_info_layout, null);

        builder.setView(view);

        TextView titleTextView = view.findViewById(R.id.titleTimelineTextView);
        TextView commentTextView = view.findViewById(R.id.commentTimelineTextView);
        TextView dateTextView = view.findViewById(R.id.dateTimelineTextView);
        TextView urgencyTextView = view.findViewById(R.id.urgencyTimelineTextView);

        dateTextView.setText(getArguments().getString("Date") + " " + getArguments().getString("Time"));
        titleTextView.setText(getArguments().getString("Title"));
        commentTextView.setText(getArguments().getString("Comment"));
        urgencyTextView.setText(getArguments().getString("Urgency"));

        return builder.setTitle("Info").create();
    }

}

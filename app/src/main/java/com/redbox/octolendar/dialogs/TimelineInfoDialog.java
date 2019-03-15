package com.redbox.octolendar.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.redbox.octolendar.R;


//Displays a dialog for an Event, used in TimelineFragment
public class TimelineInfoDialog extends android.support.v4.app.DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.timeline_info_layout, null);

        builder.setView(view);

        TextView titleTextView = view.findViewById(R.id.titleTimelineTextView);
        TextView commentTextView = view.findViewById(R.id.commentTimelineTextView);
        TextView dateTextView = view.findViewById(R.id.dateTimelineTextView);
        TextView timeTextView = view.findViewById(R.id.timeInfoTextView);
        TextView completedTextView = view.findViewById(R.id.completedTextView);


        dateTextView.setText(getArguments().getString("Date"));
        titleTextView.setText(getArguments().getString("Title"));
        commentTextView.setText(getArguments().getString("Comment"));
        timeTextView.setText(getArguments().getString("Time"));

        if (getArguments().getString("Completed").equals("1")){
            completedTextView.setText(R.string.string_done);
        }
        else completedTextView.setText(R.string.string_not_done);

        return builder.setTitle("Info").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create();
    }

}

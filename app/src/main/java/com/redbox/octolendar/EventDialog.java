package com.redbox.octolendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.redbox.octolendar.database.model.Event;
import com.redbox.octolendar.utilities.UtilityFunctionsClass;

public class EventDialog extends AppCompatDialogFragment {

    private EditText heading;
    private EditText comment;
    private TimePicker timepick;
    private String time;
    private RadioGroup urgency;
    private String urgencyType;
    private LayoutInflater inflater;

    private EventDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        inflater = getActivity().getLayoutInflater();


        View view = inflater.inflate(R.layout.add_event_dialog, null);

        UtilityFunctionsClass.hideNavBar(view);
        time = UtilityFunctionsClass.getCurrentTime();

        builder.setView(view).setTitle("Add an event").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Event event = new Event(0, " ", time, heading.getText().toString(), comment.getText().toString(), urgencyType);

                listener.applyInfo(event);

            }
        });

        heading = view.findViewById(R.id.titleEditText);
        comment = view.findViewById(R.id.commentEditText);
        timepick = view.findViewById(R.id.timePicker);
        urgency = view.findViewById(R.id.urgencyRadioGroup);

        timepick.setIs24HourView(true);

        timepick.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                time = UtilityFunctionsClass.prepareStringTime(hour, minute);
            }
        });

        urgency.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int childCount = urgency.getChildCount();
                for (int x = 0; x < childCount; x++) {
                    RadioButton btn = (RadioButton) urgency.getChildAt(x);
                    if (btn.getId() == i) {
                        urgencyType = btn.getText().toString();
                    }
                }
            }
        });

        return builder.create();
    }

    public interface EventDialogListener {
        void applyInfo(Event event);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (EventDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Does not implement class Listener");
        }
    }

}

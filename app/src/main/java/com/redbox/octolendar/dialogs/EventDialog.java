package com.redbox.octolendar.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.redbox.octolendar.R;
import com.redbox.octolendar.database.model.Event;
import com.redbox.octolendar.utilities.UtilityFunctionsClass;

public class EventDialog extends AppCompatDialogFragment {

    private EditText headingEditText;
    private EditText commentEditText;
    private TimePicker timepick;
    private String time;
    private RadioGroup urgencyRadioGroup;
    private String urgencyType;
    private LayoutInflater inflater;
    private EventDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        try {
            inflater = getActivity().getLayoutInflater();
        } catch (NullPointerException e) {
            Log.d("NullPointerException: ", "NullPointerException while creating dialogue window");
        }


        View view = inflater.inflate(R.layout.add_event_dialog, null);

        time = UtilityFunctionsClass.getCurrentTime();

        builder.setView(view).setTitle("Add an event").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (!headingEditText.getText().toString().isEmpty()) {
                    if (commentEditText.getText().toString().equals("-")) {
                        Event event = new Event(0, time, headingEditText.getText().toString(), "-", urgencyType, " "," ", " ", 0);
                        listener.applyInfo(event);
                    } else {
                        Event event = new Event(0, time, headingEditText.getText().toString(), commentEditText.getText().toString(), urgencyType, " ", " ", " ", 0);
                        listener.applyInfo(event);
                    }
                } else {
                    Toast toast = Toast.makeText(getContext(), "Each event requires a title", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        headingEditText = view.findViewById(R.id.titleEditText);
        commentEditText = view.findViewById(R.id.commentEditText);
        timepick = view.findViewById(R.id.timePicker);
        urgencyRadioGroup = view.findViewById(R.id.urgencyRadioGroup);

        commentEditText.setText(R.string.string_edittext_placeholder);
        RadioButton btn = (RadioButton) urgencyRadioGroup.getChildAt(1);
        btn.setChecked(true);
        urgencyType = btn.getText().toString();

        timepick.setIs24HourView(true);

        timepick.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                time = UtilityFunctionsClass.prepareStringTime(hour, minute);
            }
        });

        urgencyRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int childCount = urgencyRadioGroup.getChildCount();
                for (int x = 0; x < childCount; x++) {
                    RadioButton btn = (RadioButton) urgencyRadioGroup.getChildAt(x);
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

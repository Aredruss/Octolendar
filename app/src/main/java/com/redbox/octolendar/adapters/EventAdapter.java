package com.redbox.octolendar.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import com.redbox.octolendar.EventManagerActivity;
import com.redbox.octolendar.database.DatabaseHelper;
import com.redbox.octolendar.database.model.Event;
import com.redbox.octolendar.R;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private Context context;
    private List<Event> list;
    private DatabaseHelper db;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView timeTextView;
        private TextView titleTextView;
        private TextView commentTextView;
        private TextView urgencyTextView;
        private CheckBox doneCheckBox;
        private ImageButton editButton;
        private ImageButton shareButton;
        private ImageButton deleteButton;
        private TextView doneTextView;

        private TableLayout infoTable;
        private ImageButton infoButton;


        public ViewHolder(View view) {
            super(view);
            timeTextView = view.findViewById(R.id.timeTextView);
            titleTextView = view.findViewById(R.id.titleTextView);
            commentTextView = view.findViewById(R.id.commentTextView);
            urgencyTextView = view.findViewById(R.id.urgencyTextView);
            doneCheckBox = view.findViewById(R.id.doneCheckBox);
            deleteButton = view.findViewById(R.id.deleteButton);
            shareButton = view.findViewById(R.id.shareButton);
            editButton = view.findViewById(R.id.editButton);
            doneTextView = view.findViewById(R.id.doneTextView);

            infoTable = view.findViewById(R.id.infoTable);
            infoButton = view.findViewById(R.id.infoButton);

        }

        public ImageButton getDeleteButton() {
            return deleteButton;
        }

    }

    public EventAdapter(Context context, List<Event> eventlist) {
        this.context = context;
        this.list = eventlist;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        db = new DatabaseHelper(context);
        Event event = list.get(holder.getAdapterPosition());
        if (event.getEndTime() != null)
            holder.timeTextView.setText(event.getStartTime() + "-" + event.getEndTime());
        else holder.timeTextView.setText(event.getStartTime());
        holder.titleTextView.setText(event.getTitle());
        holder.commentTextView.setText(event.getComment());
        holder.urgencyTextView.setText(event.getUrgency());

        if (event.getCompleted() == 1) {
            holder.doneTextView.setText(R.string.string_done);
            holder.doneTextView.setTextColor(ContextCompat.getColor(context, R.color.colorTextDone));

            holder.doneCheckBox.setChecked(true);
        } else {
            holder.doneTextView.setText(R.string.string_not_done);
            holder.doneTextView.setTextColor(ContextCompat.getColor(context, R.color.colorTextNotDone));
        }

        holder.doneCheckBox.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            if (isChecked) {
                holder.doneTextView.setText(R.string.string_done);
                holder.doneTextView.setTextColor(ContextCompat.getColor(context, R.color.colorTextDone));
                event.setCompleted(1);
                db.updateEvent(event);
            } else {
                holder.doneTextView.setText(R.string.string_not_done);
                holder.doneTextView.setTextColor(ContextCompat.getColor(context, R.color.colorTextNotDone));
                event.setCompleted(0);
                db.updateEvent(event);
            }
        });

        holder.infoButton.setOnClickListener((View v)->{
            Log.d("VJH", "onBindViewHolder: "+ "click");
            holder.infoTable.setVisibility(View.VISIBLE);
        });

        //Edit Event
        holder.editButton.setOnClickListener((View v) -> {
            Intent intent = new Intent(v.getContext(), EventManagerActivity.class);
            intent.putExtra("Event", event);
            v.getContext().startActivity(intent);
        });

        //Share Intent to other apps
        holder.shareButton.setOnClickListener((View v) -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, event.getShareText());
            intent.setType("text/plain");
            v.getContext().startActivity(Intent.createChooser(intent, "Send to"));
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

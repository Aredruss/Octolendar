package com.redbox.octolendar.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import com.redbox.octolendar.database.DatabaseHelper;
import com.redbox.octolendar.database.model.Event;
import com.redbox.octolendar.R;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private Context context;
    private List<Event> list;
    private DatabaseHelper db;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView timeTextView;
        public TextView titleTextView;
        public TextView commentTextView;
        public TextView urgencyTextView;
        public CardView cardEventView;
        public CheckBox doneCheckBox;


        public ViewHolder(View view){
            super(view);
            timeTextView = view.findViewById(R.id.timeTextView);
            titleTextView = view.findViewById(R.id.titleTextView);
            commentTextView = view.findViewById(R.id.commentTextView);
            urgencyTextView = view.findViewById(R.id.urgencyTextView);
            cardEventView = view.findViewById(R.id.eventCard);
            doneCheckBox = view.findViewById(R.id.doneCheckBox);
        }

    }

    public EventAdapter(Context context, List<Event> eventlist){
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
        if (event.getEndTime() !=null) holder.timeTextView.setText(event.getStartTime() + "-" + event.getEndTime());
        else holder.timeTextView.setText(event.getStartTime());
        holder.titleTextView.setText(event.getTitle());
        holder.commentTextView.setText(event.getComment());
        holder.urgencyTextView.setText(event.getUrgency());

        if (event.getCompleted() == 1){
            holder.cardEventView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorTimelineIconSecond));
            holder.doneCheckBox.setChecked(true);
        }

        holder.doneCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    holder.cardEventView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorTimelineIconSecond));
                    event.setCompleted(1);
                    db.updateEvent(event);
                }
                else{
                    holder.cardEventView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.colorAccent));
                    event.setCompleted(0);
                    db.updateEvent(event);
                }

            }
        });

    }

    @Override
    public int getItemCount(){ return list.size(); }
}

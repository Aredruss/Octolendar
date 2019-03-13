package com.redbox.octolendar.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
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

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView timeTextView;
        public TextView titleTextView;
        public TextView commentTextView;
        public TextView urgencyTextView;
        public CardView cardEventView;
        public CheckBox doneCheckBox;
        public ImageButton editButton;
        public ImageButton deleteButton;
        public RecyclerView recyclerView;


        public ViewHolder(View view){
            super(view);
            timeTextView = view.findViewById(R.id.timeTextView);
            titleTextView = view.findViewById(R.id.titleTextView);
            commentTextView = view.findViewById(R.id.commentTextView);
            urgencyTextView = view.findViewById(R.id.urgencyTextView);
            cardEventView = view.findViewById(R.id.eventCard);
            doneCheckBox = view.findViewById(R.id.doneCheckBox);
            editButton = view.findViewById(R.id.editButton);
            deleteButton = view.findViewById(R.id.deleteButton);
            recyclerView = view.findViewById(R.id.cardFragmentRecyclerView);
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
            holder.cardEventView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorCardBackgroundDone));
            holder.doneCheckBox.setChecked(true);
        }
        else{
            holder.cardEventView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorCardBackgroundNotDone));

        }

        holder.doneCheckBox.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) ->{
                if(isChecked){
                    holder.cardEventView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorCardBackgroundDone));
                    event.setCompleted(1);
                    db.updateEvent(event);
                }
                else{
                    holder.cardEventView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.colorCardBackgroundNotDone));
                    event.setCompleted(0);
                    db.updateEvent(event);
                }
        });

        holder.editButton.setOnClickListener((View v) ->{
            Intent intent = new Intent(v.getContext(), EventManagerActivity.class);
            intent.putExtra("Event", event);
            v.getContext().startActivity(intent);
        });

        holder.deleteButton.setOnClickListener((View v) ->{
//            int index = holder.getAdapterPosition();
//             super.notifyItemRemoved(index);
//            Log.d("T", "onBindViewHolder: " + holder.getAdapterPosition());
//            holder.recyclerView.removeViewAt(index);
//            db.deleteEvent(event);
        });

    }

    @Override
    public int getItemCount(){ return list.size(); }
}

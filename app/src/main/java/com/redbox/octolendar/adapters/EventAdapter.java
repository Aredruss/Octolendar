package com.redbox.octolendar.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.redbox.octolendar.database.model.Event;
import com.redbox.octolendar.R;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private Context context;
    private List<Event> list;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView timeTextView;
        public TextView titleTextView;
        public TextView commentTextView;
        public TextView urgencyTextView;

        public ViewHolder(View view){
            super(view);
            timeTextView = view.findViewById(R.id.timeTextView);
            titleTextView = view.findViewById(R.id.titleTextView);
            commentTextView = view.findViewById(R.id.commentTextView);
            urgencyTextView = view.findViewById(R.id.urgencyTextView);
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
        Event event = list.get(position);
        holder.timeTextView.setText(event.getTime());
        holder.titleTextView.setText(event.getTitle());
        holder.commentTextView.setText(event.getComment());
        holder.urgencyTextView.setText(event.getUrgency());
    }

    @Override
    public int getItemCount(){ return list.size(); }
}

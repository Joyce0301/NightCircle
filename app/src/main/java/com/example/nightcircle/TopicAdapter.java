package com.example.nightcircle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {

    public interface OnTopicClickListener {
        void onTopicClick(Topic topic);
    }

    private List<Topic> topics;
    private OnTopicClickListener listener;

    public TopicAdapter(List<Topic> topics, OnTopicClickListener listener) {
        this.topics = topics;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_topic, parent, false);
        return new TopicViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        Topic t = topics.get(position);
        holder.tvTitle.setText(t.getTitle());
        holder.tvMood.setText(t.getMood());
        holder.tvSeats.setText(t.getCurrentUsers() + " / " + t.getCapacity());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onTopicClick(t);
        });
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    static class TopicViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvMood, tvSeats;

        TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvMood  = itemView.findViewById(R.id.tvMood);
            tvSeats = itemView.findViewById(R.id.tvSeats);
        }
    }
}


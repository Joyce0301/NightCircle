package com.example.nightcircle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private final List<ChatMessage> list;

    public ChatAdapter(List<ChatMessage> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new ChatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatMessage msg = list.get(position);
        holder.tvSender.setText(msg.sender);
        holder.tvContent.setText(msg.content);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView tvSender;
        TextView tvContent;

        ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSender = itemView.findViewById(R.id.tvSender);
            tvContent = itemView.findViewById(R.id.tvContent);
        }
    }
}




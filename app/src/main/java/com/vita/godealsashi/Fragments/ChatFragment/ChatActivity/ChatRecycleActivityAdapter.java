package com.vita.godealsashi.Fragments.ChatFragment.ChatActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vita.godealsashi.Fragments.ChatFragment.ChatFragment;
import com.vita.godealsashi.Fragments.ChatFragment.ChatRecycleAdapter;
import com.vita.godealsashi.ParseClasses.ChatClass;
import com.vita.godealsashi.R;

import java.util.List;

public class ChatRecycleActivityAdapter extends RecyclerView.Adapter<ChatRecycleActivityAdapter.ViewHolder> {

    private Context context;
    private List<ChatClass> chatMessages;

    public ChatRecycleActivityAdapter(List<ChatClass> chatMessages) {
        this.chatMessages = chatMessages;
    }

    @NonNull
    @Override
    public ChatRecycleActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //there i put it to xml file
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_message_item, viewGroup, false);
        context = viewGroup.getContext();

        return new ChatRecycleActivityAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ChatRecycleActivityAdapter.ViewHolder viewHolder, int i) {

        //Here comes setters and get values from db
        viewHolder.setIsRecyclable(false);


        String message = chatMessages.get(i).getMessage();
        viewHolder.setMessage(message);

        String sender = String.valueOf(chatMessages.get(i).getSender());
        viewHolder.setSender(sender);


    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private TextView user_message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setMessage(String message){

            user_message = mView.findViewById(R.id.user_message);
            user_message.setText("message: " + message);


        }

        public void setSender(String sender){

            user_message = mView.findViewById(R.id.sender_text);
            user_message.setText("sender: " + sender);


        }
    }
}

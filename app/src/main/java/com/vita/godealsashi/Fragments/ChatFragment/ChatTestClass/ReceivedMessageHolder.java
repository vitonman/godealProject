package com.vita.godealsashi.Fragments.ChatFragment.ChatTestClass;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.utils.Utils;
import com.vita.godealsashi.ParseClasses.Message;
import com.vita.godealsashi.R;

import java.util.Date;

public class ReceivedMessageHolder extends RecyclerView.ViewHolder {

    TextView messageText, timeText, nameText;
    ImageView profileImage;

    ReceivedMessageHolder(View itemView) {
        super(itemView);
        messageText = (TextView) itemView.findViewById(R.id.text_message_body);
        timeText = (TextView) itemView.findViewById(R.id.text_message_time);
        nameText = (TextView) itemView.findViewById(R.id.text_message_name);
        profileImage = (ImageView) itemView.findViewById(R.id.image_message_profile);
    }

    void bind(Message message) {
        messageText.setText(message.getBody());

        // Format the stored timestamp into a readable String using method.
        //timeText.setText(DateUtils.formatDateTime(message.getCreatedAt()));
        nameText.setText(message.getUserId());

        // Insert the profile image from the URL into the ImageView.
       // Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage);
    }
}

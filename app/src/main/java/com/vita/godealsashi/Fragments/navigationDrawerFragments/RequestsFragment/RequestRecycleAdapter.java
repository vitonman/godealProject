package com.vita.godealsashi.Fragments.navigationDrawerFragments.RequestsFragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.vita.godealsashi.ParseClasses.CustomUser;
import com.vita.godealsashi.ParseClasses.Invite;
import com.vita.godealsashi.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RequestRecycleAdapter extends RecyclerView.Adapter<RequestRecycleAdapter.ViewHolder> {

    public List<CustomUser> userList;

    public Context context;

    private Button user_button;

    int button_set;

    public RequestRecycleAdapter(List<CustomUser> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public RequestRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.userlist_accept_friend_item, viewGroup, false);
        context = viewGroup.getContext();

        button_set = 0;

        user_button = view.findViewById(R.id.add_friend_btn);


        return new RequestRecycleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RequestRecycleAdapter.ViewHolder viewHolder, int i) {

        viewHolder.setIsRecyclable(false);

        String name = userList.get(i).getName();
        viewHolder.setUserName(name);

        //add to friends

        //---------------------------------------------------
        final ParseUser whoSendYo = userList.get(i).getOwner();

        ParseUser current_user = ParseUser.getCurrentUser();

        //----------------------------------------------------

        final ParseQuery<Invite> query = ParseQuery.getQuery(Invite.class);
        query.whereEqualTo("target", current_user);
        query.whereEqualTo("owner", whoSendYo);



        user_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(button_set == 0){

                    query.getFirstInBackground(new GetCallback<Invite>() {
                        @Override
                        public void done(Invite object, ParseException e) {

                            object.setAccept(true);
                            object.saveInBackground();

                        }
                    });

                    button_set = 1;
                } else {

                    query.getFirstInBackground(new GetCallback<Invite>() {
                        @Override
                        public void done(Invite object, ParseException e) {

                            object.setAccept(false);
                            object.saveInBackground();

                        }
                    });

                    button_set = 0;

                }



            }
        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private View mView;

        //user class data
        private TextView user_age_text;
        private TextView user_name_text;
        //private CircleImageView user_image;
        private CircleImageView user_image;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;



        }



        public void setUserName(String name){

            user_name_text = mView.findViewById(R.id.user_name);
            user_name_text.setText(name);


        }

    }





}

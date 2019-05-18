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

import com.bumptech.glide.Glide;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.vita.godealsashi.CustomClasses.CustomUser;
import com.vita.godealsashi.R;
import com.vita.godealsashi.User.FriendRequest;
import com.vita.godealsashi.User.UserProfileActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RequestRecycleAdapter extends RecyclerView.Adapter<RequestRecycleAdapter.ViewHolder> {

    public List<CustomUser> userList;

    public List<FriendRequest> requestsList;

    public Context context;

    private Button user_button;

    public RequestRecycleAdapter(List<CustomUser> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public RequestRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.userlist_accept_friend_item, viewGroup, false);
        context = viewGroup.getContext();

        user_button = view.findViewById(R.id.add_friend_btn);


        return new RequestRecycleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RequestRecycleAdapter.ViewHolder viewHolder, int i) {

        viewHolder.setIsRecyclable(false);

        String name = userList.get(i).getName();
        viewHolder.setUserName(name);


        //add to friends

        final String objectId = userList.get(i).getObjectId();
        user_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setCurrentUserFriendList(objectId, ParseUser.getCurrentUser());
                deleteSentInvite(objectId, ParseUser.getCurrentUser());


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

    private void deleteSentInvite(final String object_user_id, final ParseUser current_user){

        final ParseQuery<FriendRequest> queryExist = ParseQuery.getQuery(FriendRequest.class);

        queryExist.whereEqualTo("user", current_user);
        queryExist.getFirstInBackground(new GetCallback<FriendRequest>() {
            @Override
            public void done(FriendRequest object, ParseException e) {

                if(e == null){

                    if(object.getRecived() == null){

                        //THERE NO OBJECT

                        Toast.makeText(context, "Error null", Toast.LENGTH_SHORT).show();

                    } else if(object.getRecived() != null){


                        JSONArray recivedArray = object.getRecived();


                        for (int i = 0; i < recivedArray.length(); i++) {

                            try {
                                while(recivedArray.get(i).equals(object_user_id)){

                                    recivedArray.remove(i);

                                    String current_user_object = object.getObjectid();
                                    deleteRecivesInvite(object_user_id, current_user_object);
                                    object.setRecived(recivedArray);
                                    object.saveInBackground();

                                }


                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    } else {

                        //something wrong


                    }
                } else {

                    Log.d("Error: ", e.getMessage());

                }

            }
        });

    }

    private void deleteRecivesInvite(final String object_user_id, final String current_user){

        final ParseQuery<FriendRequest> queryExist = ParseQuery.getQuery(FriendRequest.class);

        queryExist.whereEqualTo("objectid", object_user_id);
        queryExist.getFirstInBackground(new GetCallback<FriendRequest>() {
            @Override
            public void done(FriendRequest object, ParseException e) {

                if(e == null){

                    if(object.getSent() == null){

                        //THERE NO OBJECT

                    } else if(object.getSent() != null){

                        JSONArray sentArray = object.getSent();
                        try {
                            for (int i = 0; i < sentArray.length(); i++) {

                                while(sentArray.get(i).equals(current_user)){

                                    sentArray.remove(i);
                                    object.setSent(sentArray);
                                    object.saveInBackground();


                                }

                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    } else {

                        //LOG something wrong with get data

                    }
                } else {

                    Log.d("Error: ", e.getMessage());

                }

            }
        });
    }

    private void setCurrentUserFriendList(final String ownerId, ParseUser current_user){

        final ParseQuery<FriendRequest> queryExist = ParseQuery.getQuery(FriendRequest.class);
        queryExist.whereEqualTo("user", current_user);

        queryExist.getFirstInBackground(new GetCallback<FriendRequest>() {
            @Override
            public void done(FriendRequest object, ParseException e) {


                if(e == null){

                    if(object.getFriendlist() == null || object.getFriendlist().length() <= 0){

                        JSONArray friendArray = new JSONArray();
                        friendArray.put(ownerId);

                        object.setFriendlist(friendArray);
                        setOwnerFriendList(ownerId, object.getObjectid());
                        object.saveInBackground();

                        Log.d("Message: ", "Clear, added new array");

                    } else if(object.getFriendlist() != null){

                        JSONArray friendArray = object.getFriendlist();

                        try{
                            for (int i = 0; i < friendArray.length(); i++) {

                                while(friendArray.get(i).equals(ownerId)){

                                    friendArray.remove(i);
                                    object.setFriendlist(friendArray);
                                    object.saveInBackground();

                                }

                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                        friendArray.put(ownerId);
                        object.setFriendlist(friendArray);

                        setOwnerFriendList(ownerId, object.getObjectid());
                        object.saveInBackground();

                    }

                } else {

                    Log.d("Error: ", e.getMessage());

                }
            }
        });



    }

    private void setOwnerFriendList(final String ownerId, final String current_user_id){

        final ParseQuery<FriendRequest> queryExist = ParseQuery.getQuery(FriendRequest.class);
        queryExist.whereEqualTo("objectid", ownerId);

        queryExist.getFirstInBackground(new GetCallback<FriendRequest>() {
            @Override
            public void done(FriendRequest object, ParseException e) {


                if(e == null){

                    if(object.getFriendlist() == null || object.getFriendlist().length() <= 0){

                        JSONArray friendArray = new JSONArray();
                        friendArray.put(current_user_id);

                        object.setFriendlist(friendArray);
                        object.saveInBackground();

                        Log.d("Message: ", "Clear, added new array");

                    } else if(object.getFriendlist() != null){

                        JSONArray friendArray = object.getFriendlist();

                        try{
                            for (int i = 0; i < friendArray.length(); i++) {

                                while(friendArray.get(i).equals(current_user_id)){

                                    friendArray.remove(i);
                                    object.setFriendlist(friendArray);
                                    object.saveInBackground();

                                }

                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                        friendArray.put(current_user_id);
                        object.setFriendlist(friendArray);
                        object.saveInBackground();

                    }

                } else {

                    Log.d("Error: ", e.getMessage());

                }
            }
        });



    }
}

package com.vita.godealsashi.Fragments.navigationDrawerFragments.ColleguesFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.vita.godealsashi.MainActivity;
import com.vita.godealsashi.ParseClasses.CustomUser;
import com.vita.godealsashi.ParseClasses.FriendList;
import com.vita.godealsashi.ParseClasses.Invite;
import com.vita.godealsashi.R;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ColleguesFragment extends Fragment {

    private RecyclerView user_list_view;
    private ColleguesRecycleAdapter colleguesRecycleAdapter;

    private List<CustomUser> user_list;
    private TextView user_name_textview;

    public Context context;


    public ColleguesFragment(){


    }

    public static ColleguesFragment newInstance() {
        return new ColleguesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.collegues_fragment, container, false);

     /*   progressBar = v.findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);*/
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String custom_user_current_id = preferences.getString("current_ownerId", "");

        user_list = new ArrayList<>();
        user_list_view = v.findViewById(R.id.collegues_user_list);


        colleguesRecycleAdapter = new ColleguesRecycleAdapter(user_list);

        user_list_view.setLayoutManager(new LinearLayoutManager(container.getContext()));
        user_list_view.setHasFixedSize(true);
        user_list_view.setAdapter(colleguesRecycleAdapter);


        final ParseUser currentUser = ParseUser.getCurrentUser();

        if (currentUser != null) {
            //Toast.makeText(getActivity(), "Success fragment", Toast.LENGTH_SHORT).show();

                user_list.clear();

                user_list_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        Boolean reqchedBottom = !recyclerView.canScrollVertically(1);

                        if(reqchedBottom){

                            //loadMorePost();

                        }

                    }
                });

            checkForFriends(custom_user_current_id);



            }




        // Inflate the layout for this fragment
        return v;
    }



    private void checkForFriends(String custom_user_current_id){


        ParseQuery<FriendList> query = ParseQuery.getQuery(FriendList.class);

        query.whereEqualTo("targetId", custom_user_current_id);

        query.findInBackground(new FindCallback<FriendList>() {
            @Override
            public void done(List<FriendList> objects, ParseException e) {

                List<String> objectsIds = new ArrayList<>();

                for (FriendList object: objects){

                    objectsIds.add(object.getOwner());

                }

                getFriendList(objectsIds);

            }
        });


    }

    private void getFriendList(List<String> objectsIds){

        ParseQuery<CustomUser> query = ParseQuery.getQuery(CustomUser.class);
        query.whereContainedIn("objectId", objectsIds);

        query.findInBackground(new FindCallback<CustomUser>() {
            @Override
            public void done(List<CustomUser> objects, ParseException e) {


                if(e == null){

                    user_list.addAll(objects);


                }

                colleguesRecycleAdapter.notifyDataSetChanged();

            }
        });

    }




}

package com.vita.godealsashi.Fragments.navigationDrawerFragments.ColleguesFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.parse.livequery.ParseLiveQueryClient;
import com.parse.livequery.SubscriptionHandling;
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
import java.util.Set;

import static com.parse.Parse.getApplicationContext;


public class ColleguesFragment extends Fragment {

    private String TARGER_USER_ID = "targetUserId";

    private RecyclerView user_list_view;
    private ColleguesRecycleAdapter colleguesRecycleAdapter;

    private List<CustomUser> user_list;
    private TextView user_name_textview;
    Set<String> friend_list;

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

        final String currentUserId = preferences.getString("currentUserId", "");

        friend_list = preferences.getStringSet("friendlist", null);



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


            if(friend_list != null){

                getFriendList(friend_list);

            } else {

                Toast.makeText(getApplicationContext(), "friend_list have no data", Toast.LENGTH_SHORT).show();
            }





            }




        // Inflate the layout for this fragment
        return v;
    }


    private void getFriendList(Set<String> objectsIds){


            ParseQuery<CustomUser> query = ParseQuery.getQuery(CustomUser.class);
            query.whereContainedIn("objectId", objectsIds);

            query.findInBackground(new FindCallback<CustomUser>() {
                @Override
                public void done(List<CustomUser> objects, ParseException e) {


                    if(e == null){

                        user_list.addAll(objects);


                    } else {

                        e.getMessage();

                    }

                    colleguesRecycleAdapter.notifyDataSetChanged();

                }
            });





    }




}

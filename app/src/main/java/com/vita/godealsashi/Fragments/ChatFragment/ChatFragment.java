package com.vita.godealsashi.Fragments.ChatFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.livequery.ParseLiveQueryClient;
import com.parse.livequery.SubscriptionHandling;
import com.vita.godealsashi.ParseClasses.CustomUser;


import com.vita.godealsashi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {



    private RecyclerView user_list_view;
    private ChatRecycleAdapter userChatRecycleAdapter;

    private List<CustomUser> user_list;

    private TextView user_name_textview;

    private ProgressBar progressBar;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_chat, container, false);

        progressBar = v.findViewById(R.id.progressBar_chat);

        user_list = new ArrayList<>();
        user_list_view = v.findViewById(R.id.user_list_view);


        userChatRecycleAdapter = new ChatRecycleAdapter(user_list);

        user_list_view.setLayoutManager(new LinearLayoutManager(container.getContext()));
        user_list_view.setHasFixedSize(true);
        user_list_view.setAdapter(userChatRecycleAdapter);

        final ParseUser currentUser = ParseUser.getCurrentUser();

        if (currentUser != null) {
            //Toast.makeText(getActivity(), "Success fragment", Toast.LENGTH_SHORT).show();

            progressBar.setVisibility(View.VISIBLE);

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


            getData();


            ParseLiveQueryClient parseLiveQueryClient = ParseLiveQueryClient.Factory.getClient();

            ParseQuery<CustomUser> parseQuery = ParseQuery.getQuery(CustomUser.class);
            SubscriptionHandling<CustomUser> subscriptionHandling = parseLiveQueryClient.subscribe(parseQuery);

            subscriptionHandling.handleEvents(new SubscriptionHandling.HandleEventsCallback<CustomUser>() {
                @Override
                public void onEvents(ParseQuery<CustomUser> query, SubscriptionHandling.Event event, CustomUser object) {
                    // HANDLING all events
                    getData();
                }
            });






        } else {
            // show the signup or login screen
            Toast.makeText(this.getActivity(), "Logging out", Toast.LENGTH_SHORT).show();
        }









        // Inflate the layout for this fragment
        return v;
    }

    private void getData(){

        ParseQuery<CustomUser> query = ParseQuery.getQuery(CustomUser.class);
        //query.whereNotEqualTo("owner", currentUser);
        query.findInBackground(new FindCallback<CustomUser>() {
            @Override
            public void done(List<CustomUser> results, ParseException e) {
                if(e == null){
                    user_list.clear();

                    progressBar.setVisibility(View.INVISIBLE);


                    for (CustomUser r: results){

                        // Do whatever you want with the data...

                        user_list.add(r);

                        //Toast.makeText(getActivity(), age, Toast.LENGTH_LONG).show();

                    }
                    userChatRecycleAdapter.notifyDataSetChanged();



                } else {

                    Toast.makeText(getActivity(), "Something wrong", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);

                }
            }
        });

    }

}

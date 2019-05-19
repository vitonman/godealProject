package com.vita.godealsashi.Fragments.navigationDrawerFragments.ColleguesFragment;

import android.content.Context;
import android.os.Bundle;
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

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.vita.godealsashi.ParseClasses.CustomUser;
import com.vita.godealsashi.ParseClasses.Invite;
import com.vita.godealsashi.R;

import org.json.JSONException;

import java.util.ArrayList;
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

                checkForRecivedInvites(currentUser);

            }


        // Inflate the layout for this fragment
        return v;
    }

    private void getFriendList(ParseUser friend){

        ParseQuery<CustomUser> query = ParseQuery.getQuery(CustomUser.class);

        query.whereEqualTo("owner", friend);

        query.getFirstInBackground(new GetCallback<CustomUser>() {
            @Override
            public void done(CustomUser object, ParseException e) {

                if(e == null){

                    user_list.add(object);

                    colleguesRecycleAdapter.notifyDataSetChanged();

                } else {

                    Toast.makeText(getActivity(), "Something wrong", Toast.LENGTH_LONG).show();

                }

            }
        });
    }

    private void checkForRecivedInvites(ParseUser current_user){

        ParseQuery<Invite> query = ParseQuery.getQuery(Invite.class);

        query.whereEqualTo("target", current_user);
        query.findInBackground(new FindCallback<Invite>() {
            @Override
            public void done(List<Invite> objects, ParseException e) {

                for (Invite object: objects){

                    getFriendList(object.getOwner());

                }

            }
        });


    }




}

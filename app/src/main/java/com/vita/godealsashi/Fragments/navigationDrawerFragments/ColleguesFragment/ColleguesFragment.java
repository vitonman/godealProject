package com.vita.godealsashi.Fragments.navigationDrawerFragments.ColleguesFragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.vita.godealsashi.CustomClasses.CustomUser;
import com.vita.godealsashi.Fragments.DealFragment.DealRecycleAdapter;
import com.vita.godealsashi.R;

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
                ParseQuery<CustomUser> query = ParseQuery.getQuery(CustomUser.class);
                query.findInBackground(new FindCallback<CustomUser>() {
                    @Override
                    public void done(List<CustomUser> results, ParseException e) {

                        if(e == null){


                            for (CustomUser r: results){
                                // Do whatever you want with the data...
                                if(r != null){

                                    user_list.add(r);

                                } else {

                                    // something went wrong


                                }



                            }

                            colleguesRecycleAdapter.notifyDataSetChanged();

                        } else {

                            Toast.makeText(getActivity(), "Something wrong", Toast.LENGTH_LONG).show();

                        }


                    }
                });

            }


        // Inflate the layout for this fragment
        return v;
    }




}
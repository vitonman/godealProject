package com.vita.godealsashi.Fragments.DealFragment.rethinkDeal;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.vita.godealsashi.Fragments.DealFragment.DealRecycleAdapter;
import com.vita.godealsashi.ParseClasses.CustomUser;
import com.vita.godealsashi.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DealFragmentTest extends Fragment {

    private RecyclerView user_list_view;
    private DealRecycleAdapter userDealRecycleAdapter;

    private List<CustomUser> user_list;

    private TextView user_name_textview;

    private Button searchWorkers;

    private Spinner city_spinner;
    private Spinner ability_spinner;

    private ProgressBar progressBar;


    public DealFragmentTest() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_deal, container, false);

        progressBar = v.findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);

        user_list = new ArrayList<>();
        user_list_view = v.findViewById(R.id.user_list_view);


        userDealRecycleAdapter = new DealRecycleAdapter(user_list);

        user_list_view.setLayoutManager(new LinearLayoutManager(container.getContext()));
        user_list_view.setHasFixedSize(true);
        user_list_view.setAdapter(userDealRecycleAdapter);


        searchWorkers = (Button) v.findViewById(R.id.setup_activity_btn);


        final ParseUser currentUser = ParseUser.getCurrentUser();

        if (currentUser != null) {
            //Toast.makeText(getActivity(), "Success fragment", Toast.LENGTH_SHORT).show();

            searchWorkers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    progressBar.setVisibility(View.VISIBLE);

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
                    query.whereEqualTo("city", city_spinner.getSelectedItem().toString());
                    query.whereContains("ability", ability_spinner.getSelectedItem().toString());
                    query.findInBackground(new FindCallback<CustomUser>() {
                        @Override
                        public void done(List<CustomUser> results, ParseException e) {

                            if(e == null){

                                progressBar.setVisibility(View.INVISIBLE);

                                for (CustomUser r: results){
                                    // Do whatever you want with the data...
                                    if(r != null){

                                        user_list.add(r);

                                    } else {

                                        // something went wrong

                                    }

                                }

                                userDealRecycleAdapter.notifyDataSetChanged();

                            } else {

                                progressBar.setVisibility(View.INVISIBLE);

                                Toast.makeText(getActivity(), "Something wrong", Toast.LENGTH_LONG).show();

                            }


                        }
                    });

                }
            });





        } else {
            // show the signup or login screen
            Toast.makeText(this.getContext(), "Logging out", Toast.LENGTH_SHORT).show();
        }






        // Inflate the layout for this fragment
        return v;
    }


}

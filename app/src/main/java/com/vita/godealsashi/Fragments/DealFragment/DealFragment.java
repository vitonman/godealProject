package com.vita.godealsashi.Fragments.DealFragment;

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
import com.vita.godealsashi.CustomClasses.CustomUser;
import com.vita.godealsashi.Fragments.ChatFragment.ChatRecycleAdapter;
import com.vita.godealsashi.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DealFragment extends Fragment {

    private RecyclerView user_list_view;
    private DealRecycleAdapter userDealRecycleAdapter;

    private List<CustomUser> user_list;

    private TextView user_name_textview;

    private Button searchWorkers;

    private Spinner city_spinner;
    private Spinner ability_spinner;

    private ProgressBar progressBar;


    public DealFragment() {
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

        //***********************************************************************************

        //Spinner CITY
        //get the spinner from the xml.
        final Spinner city_spinner = v.findViewById(R.id.spinner_city);
        //create a list of items for the spinner.
        String[] city_items = new String[]{"Tallinn", "Tartu", "Pärnu", "Rakvere"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> city_adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, city_items);
        //set the spinners adapter to the previously created one.
        city_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        city_spinner.setAdapter(city_adapter);
        //SPINNER CITY

        //***********************************************************************************

        //SPINNER ABILITY

        final Spinner ability_spinner = v.findViewById(R.id.spinner_work);
        //create a list of items for the spinner.
        String[] ability_items = new String[]{"driver", "worker", "teacher"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> ability_adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, ability_items);
        //set the spinners adapter to the previously created one.
        ability_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        ability_spinner.setAdapter(ability_adapter);

        //SPINNER ABILITY

        //***********************************************************************************


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

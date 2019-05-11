package com.vita.godealsashi.Fragments.SearchFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.vita.godealsashi.CustomClasses.CustomUser;
import com.vita.godealsashi.Fragments.DealFragment.DealRecycleAdapter;
import com.vita.godealsashi.R;
import com.vita.godealsashi.registration.UserSetupActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements PostBtnDialog.OnInputSelected {


    private FloatingActionButton mOpenDialog;
    private TextView postName, cityName;

    private Spinner city_spinner;

    private static final String TAG = "MyCustomDialog";

    private Button searchWork;



    private RecyclerView post_list_view;
    private WorkPostRecycleAdapter wordPostRecycleAdapter;

    private List<WorkPost> post_list;
    ParseUser current_user = ParseUser.getCurrentUser();

    @Override
    public void sendInput(String postname, String city) {
        Log.d(TAG,"sendInput: found incoming input: " + postname.toString());
        Log.d(TAG,"sendInput: found incoming input: " + city.toString());
        postName.setText(postname);
        cityName.setText(city);

        WorkPost newPost = new WorkPost();

        newPost.setOwner(current_user);
        newPost.setCity(city);
        newPost.setPost(postname);
        newPost.saveInBackground();

    }




    public SearchFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_search, container, false);

        ParseUser current_user = ParseUser.getCurrentUser();

        mOpenDialog = v.findViewById(R.id.add_post_btn);
        searchWork = v.findViewById(R.id.search_work_btn);
        /*postName = v.findViewById(R.id.postname_id);
        cityName = v.findViewById(R.id.city_name);
*/

        //--------------------------------------
        //ADAPTER AND WORK SEARCH FEATURE SCROLL
        post_list = new ArrayList<>();
        post_list_view = v.findViewById(R.id.post_list_view);

        wordPostRecycleAdapter = new WorkPostRecycleAdapter(post_list);

        post_list_view.setLayoutManager(new LinearLayoutManager(container.getContext()));
        post_list_view.setHasFixedSize(true);
        post_list_view.setAdapter(wordPostRecycleAdapter);
        //ADAPTER AND WORK SEARCH FEATURE SCROLL
        //--------------------------------------

        //get the spinner from the xml.
        final Spinner city_spinner = v.findViewById(R.id.spinner_work);
        //create a list of items for the spinner.
        String[] items = new String[]{"Tallinn", "Tartu", "Pärnu", "Rakvere"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        city_spinner.setAdapter(adapter);



        mOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openDialog();

                PostBtnDialog dialog = new PostBtnDialog();
                dialog.setTargetFragment(SearchFragment.this,1);
                dialog.show(getFragmentManager(), "PostBtnDialog");

            }
        });


        if (current_user != null) {
            //Toast.makeText(getActivity(), "Success fragment", Toast.LENGTH_SHORT).show();

            searchWork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //progressBar.setVisibility(View.VISIBLE);

                    post_list.clear();

                    post_list_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            Boolean reqchedBottom = !recyclerView.canScrollVertically(1);

                            if(reqchedBottom){

                                //loadMorePost();

                            }

                        }
                    });

                    ParseQuery<WorkPost> query = ParseQuery.getQuery(WorkPost.class);
                    query.whereEqualTo("city", city_spinner.getSelectedItem().toString());
                    //query.whereContains("ability", ability_spinner.getSelectedItem().toString());
                    query.findInBackground(new FindCallback<WorkPost>() {
                        @Override
                        public void done(List<WorkPost> results, ParseException e) {

                            if(e == null){

                                //progressBar.setVisibility(View.INVISIBLE);

                                for (WorkPost r: results){
                                    // Do whatever you want with the data...
                                    if(r != null){

                                        post_list.add(r);

                                    } else {

                                        // something went wrong


                                    }



                                }

                                wordPostRecycleAdapter.notifyDataSetChanged();

                            } else {

                                //progressBar.setVisibility(View.INVISIBLE);

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



        return v;

    }



}

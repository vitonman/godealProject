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
import com.vita.godealsashi.ParseClasses.CustomUser;
import com.vita.godealsashi.R;

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

    private int anPosInt;
    private int anCityInt;

    private String[] cities = {"Tallinn", "Tartu", "Narva", "Pärnu", "Kohtla-Järve", "Viljandi","Rakvere","Maardu","Kuressaare","Sillamäe","Valga"
            ,"Võru","Jõhvi","Haapsalu","Keila","Paide","Elva","Saue","Põlva","Tapa","Jõgeva","Rapla","Kiviõli","Türi","Põltsamaa","Sindi"
            ,"Paldiski","Kärdla","Kunda","Tõrva","Narva-Jõesuu","Kehra","Loksa","Räpina","Otepää","Tamsalu","Kilingi-Nõmme","Karksi-Nuia","Antsla",
            "Võhma","Mustvee","Lihula","Suure-Jaani","Abja-Paluoja","Püssi","Mõisaküla","Kallaste"};

    private String[] positions = {"Courier services", "Repair and construction", "Logistics", "Cleaning",
            "Online-helper", "Computer-help", "Holidays & Activities", "Design", "Software & web-development",
            "Photo/Video", "Installation or Repair of home appliances", "Health and beauty", "Repair of digital equipment", "Legal assistance",
            "Tutoring and training", "Vehicle repair"};


    public DealFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();


        if (bundle != null) {

            anPosInt = bundle.getInt("positionId", 0);
            anCityInt = bundle.getInt("regionId", 0);

        } else {

            Toast.makeText(getContext(), "Bundle is null", Toast.LENGTH_SHORT).show();

        }

        //Toast.makeText(getContext(), Integer.toString(anPosInt) + ", " + Integer.toString(anCityInt), Toast.LENGTH_SHORT).show();


        View v =  inflater.inflate(R.layout.fragment_deal, container, false);

        progressBar = v.findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);

        user_list = new ArrayList<>();
        user_list_view = v.findViewById(R.id.user_list_view);


        userDealRecycleAdapter = new DealRecycleAdapter(user_list);

        user_list_view.setLayoutManager(new LinearLayoutManager(container.getContext()));
        user_list_view.setHasFixedSize(true);
        user_list_view.setAdapter(userDealRecycleAdapter);


        final String city = cities[anCityInt];
        final String position = positions[anPosInt];


        final ParseUser currentUser = ParseUser.getCurrentUser();

        if (currentUser != null) {


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
            query.whereEqualTo("city", city);
            query.whereContains("ability", position);
            query.findInBackground(new FindCallback<CustomUser>() {
                @Override
                public void done(List<CustomUser> results, ParseException e) {

                    if(e == null ){

                        progressBar.setVisibility(View.INVISIBLE);

                        for (CustomUser r: results){
                            // Do whatever you want with the data...
                            if(r != null){

                                user_list.add(r);


                            }


                        }

                        if(user_list == null || user_list.size() < 1 || results.size() < 1) {

                            Toast.makeText(getContext(), "No persons to this filter!", Toast.LENGTH_SHORT).show();
                            //TODO: make image if no data there.

                        }

                        userDealRecycleAdapter.notifyDataSetChanged();

                    } else {

                        progressBar.setVisibility(View.INVISIBLE);

                        Toast.makeText(getContext(), "Something wrong", Toast.LENGTH_LONG).show();

                    }

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

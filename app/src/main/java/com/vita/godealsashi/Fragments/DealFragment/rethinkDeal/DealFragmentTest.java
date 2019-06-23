package com.vita.godealsashi.Fragments.DealFragment.rethinkDeal;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.parse.ParseUser;
import com.vita.godealsashi.Fragments.DealFragment.DealActivities.ChoosePositionActivity.PositionActivity;
import com.vita.godealsashi.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DealFragmentTest extends Fragment {


    private Button searchWorkers;






    public DealFragmentTest() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_deal_fragment_test, container, false);

        searchWorkers = v.findViewById(R.id.search_deals);


        final ParseUser currentUser = ParseUser.getCurrentUser();

        if (currentUser != null) {
            //Toast.makeText(getActivity(), "Success fragment", Toast.LENGTH_SHORT).show();

            searchWorkers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), PositionActivity.class);
                    startActivity(intent);




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

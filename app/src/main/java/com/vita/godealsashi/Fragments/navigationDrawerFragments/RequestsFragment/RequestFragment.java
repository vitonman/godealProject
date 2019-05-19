package com.vita.godealsashi.Fragments.navigationDrawerFragments.RequestsFragment;

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

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.vita.godealsashi.ParseClasses.CustomUser;
import com.vita.godealsashi.R;
import com.vita.godealsashi.ParseClasses.FriendRequest;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class RequestFragment extends Fragment {


    private RecyclerView user_list_view;
    private RequestRecycleAdapter requestRecycleAdapter;

    private List<CustomUser> user_list;
    private List<String> recived_list;

    private TextView user_name_textview;

    public Context context;


    public RequestFragment(){


    }

    public static RequestFragment newInstance() {
        return new RequestFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_request, container, false);

     /*   progressBar = v.findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);*/

        recived_list = new ArrayList<>();

        user_list = new ArrayList<>();
        user_list_view = v.findViewById(R.id.request_user_list);


        requestRecycleAdapter = new RequestRecycleAdapter(user_list);

        user_list_view.setLayoutManager(new LinearLayoutManager(container.getContext()));
        user_list_view.setHasFixedSize(true);
        user_list_view.setAdapter(requestRecycleAdapter);


        final ParseUser currentUser = ParseUser.getCurrentUser();

        if (currentUser != null) {
            //Toast.makeText(getActivity(), "Success fragment", Toast.LENGTH_SHORT).show();

            recived_list.clear();
            user_list.clear();

            checkForRecivedInvites(currentUser);

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

            //TODO: CHECK IF PERSON HAVE SENDED TO YOU INVITE checkForInvites();



            //need to sort users where objectId =


        }


        // Inflate the layout for this fragment
        return v;
    }

    private void getRecivedUser(String sender_id){

        ParseQuery<CustomUser> query = ParseQuery.getQuery(CustomUser.class);

        query.whereEqualTo("objectId", sender_id);

        query.getFirstInBackground(new GetCallback<CustomUser>() {
            @Override
            public void done(CustomUser object, ParseException e) {

                if(e == null){

                    user_list.add(object);

                    requestRecycleAdapter.notifyDataSetChanged();

                } else {

                    Toast.makeText(getActivity(), "Something wrong", Toast.LENGTH_LONG).show();

                }

            }
        });
    }

    private void checkForRecivedInvites(ParseUser current_user){

        ParseQuery<FriendRequest> query = ParseQuery.getQuery(FriendRequest.class);

        query.whereEqualTo("user", current_user);
        query.getFirstInBackground(new GetCallback<FriendRequest>() {
            @Override
            public void done(FriendRequest object, ParseException e) {

                if(e == null) {

                    if(object.getRecived() == null){

                        // nothing there.
                        Toast.makeText(getActivity(), "You have not any invite", Toast.LENGTH_SHORT).show();

                    }else if(object.getRecived().length() >= 1){

                        try {
                            for (int i = 0; i < object.getRecived().length(); i++) {

                                String recive_id = object.getRecived().get(i).toString();
                                getRecivedUser(recive_id);

                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    } else {


                    }

                } else {

                    Log.d("Error: ", e.getMessage());

                }

            }
        });

    }

}

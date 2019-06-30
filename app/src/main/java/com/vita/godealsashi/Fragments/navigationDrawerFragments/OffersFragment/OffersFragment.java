package com.vita.godealsashi.Fragments.navigationDrawerFragments.OffersFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.livequery.ParseLiveQueryClient;
import com.parse.livequery.SubscriptionHandling;
import com.vita.godealsashi.Fragments.navigationDrawerFragments.RequestsFragment.RequestRecycleAdapter;
import com.vita.godealsashi.ParseClasses.CustomUser;
import com.vita.godealsashi.ParseClasses.Invite;
import com.vita.godealsashi.ParseClasses.OfferInvite;
import com.vita.godealsashi.R;

import java.util.ArrayList;
import java.util.List;


public class OffersFragment extends Fragment {


    private RecyclerView user_list_view;
    private RequestRecycleAdapter requestRecycleAdapter;

    private List<CustomUser> user_list;

    public Context context;

    public OffersFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_offers, container, false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String custom_user_current_id = preferences.getString("current_ownerId", "");

        user_list = new ArrayList<>();
        user_list_view = v.findViewById(R.id.offer_list_view);


        requestRecycleAdapter = new RequestRecycleAdapter(user_list);

        user_list_view.setLayoutManager(new LinearLayoutManager(container.getContext()));
        user_list_view.setHasFixedSize(true);
        user_list_view.setAdapter(requestRecycleAdapter);


        final ParseUser currentUser = ParseUser.getCurrentUser();

        if (currentUser != null) {

            ParseLiveQueryClient parseLiveQueryClient = ParseLiveQueryClient.Factory.getClient();
            ParseQuery<OfferInvite> parseQuery = ParseQuery.getQuery(OfferInvite.class);
            parseQuery.whereEqualTo("targetId", custom_user_current_id);
            SubscriptionHandling<OfferInvite> subscriptionHandling = parseLiveQueryClient.subscribe(parseQuery);

            subscriptionHandling.handleEvent(SubscriptionHandling.Event.CREATE, new SubscriptionHandling.HandleEventCallback<OfferInvite>() {
                @Override
                public void onEvent(ParseQuery<OfferInvite> query, OfferInvite object) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {

                            //DO SOME UPDATE HERE

                           /* user_list.clear();

                            checkForRecivedInvites(currentUser);*/

                            Toast.makeText(getContext(), "OFFER INVITE", Toast.LENGTH_SHORT).show();

                        }

                    });
                }
            });





            user_list.clear();


            user_list_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    Boolean reqchedBottom = !recyclerView.canScrollVertically(1);

                    if (reqchedBottom) {

                        //loadMorePost();

                    }

                }
            });


        }

        getInviteUsersList(custom_user_current_id);

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    private void getInviteUsersList(String custom_user_current_id) {


        ParseQuery<OfferInvite> query = ParseQuery.getQuery(OfferInvite.class);

        query.whereEqualTo("targetId", custom_user_current_id);

        query.findInBackground(new FindCallback<OfferInvite>() {
            @Override
            public void done(List<OfferInvite> objects, ParseException e) {

                List<String> objectsIds = new ArrayList<>();

                for (OfferInvite object : objects) {

                    objectsIds.add(object.getOwner());

                }

                getUserList(objectsIds);

            }
        });

    }

    private void getUserList(List<String> objectsIds) {

        ParseQuery<CustomUser> query = ParseQuery.getQuery(CustomUser.class);
        query.whereContainedIn("objectId", objectsIds);

        query.findInBackground(new FindCallback<CustomUser>() {
            @Override
            public void done(List<CustomUser> objects, ParseException e) {


                if (e == null) {

                    user_list.addAll(objects);

                }

                requestRecycleAdapter.notifyDataSetChanged();

            }
        });

    }

}

package com.vita.godealsashi.Fragments.navigationDrawerFragments.RequestsFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
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
import com.parse.livequery.ParseLiveQueryClient;
import com.parse.livequery.SubscriptionHandling;
import com.vita.godealsashi.MainActivity;
import com.vita.godealsashi.ParseClasses.CustomUser;
import com.vita.godealsashi.ParseClasses.FriendList;
import com.vita.godealsashi.ParseClasses.Invite;
import com.vita.godealsashi.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static com.parse.Parse.getApplicationContext;


public class RequestFragment extends Fragment {

    private String TARGER_USER_ID = "targetUserId";
    private String CURRENT_USER_ID = "ownerUserId";


    private RecyclerView user_list_view;
    private RequestRecycleAdapter requestRecycleAdapter;

    private List<CustomUser> user_list;

    private TextView user_name_textview;

    public Context context;


    public RequestFragment() {


    }

    public static RequestFragment newInstance() {
        return new RequestFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_request, container, false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String currentUserId = preferences.getString("currentUserId", "");

        user_list = new ArrayList<>();
        user_list_view = v.findViewById(R.id.request_user_list);


        requestRecycleAdapter = new RequestRecycleAdapter(user_list);

        user_list_view.setLayoutManager(new LinearLayoutManager(container.getContext()));
        user_list_view.setHasFixedSize(true);
        user_list_view.setAdapter(requestRecycleAdapter);


        final ParseUser currentUser = ParseUser.getCurrentUser();

        if (currentUser != null) {


            ParseLiveQueryClient parseLiveQueryClient = ParseLiveQueryClient.Factory.getClient();
            ParseQuery<Invite> parseQuery = ParseQuery.getQuery(Invite.class);

            // check if targetUser have a [currentUserId]
            parseQuery.whereEqualTo(TARGER_USER_ID, currentUserId);
            SubscriptionHandling<Invite> subscriptionHandling = parseLiveQueryClient.subscribe(parseQuery);

            subscriptionHandling.handleEvent(SubscriptionHandling.Event.CREATE, new SubscriptionHandling.HandleEventCallback<Invite>() {
                @Override
                public void onEvent(ParseQuery<Invite> query, Invite object) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {

                            //DO SOME UPDATE HERE

                           /* user_list.clear();

                            checkForRecivedInvites(currentUser);*/

                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();

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

        getInviteUsersList(currentUserId);

        return v;
    }


    private void getInviteUsersList(String ownerUserId) {


        ParseQuery<Invite> query = ParseQuery.getQuery(Invite.class);

        query.whereEqualTo(TARGER_USER_ID, ownerUserId);

        query.findInBackground(new FindCallback<Invite>() {
            @Override
            public void done(List<Invite> objects, ParseException e) {

                List<String> objectsIds = new ArrayList<>();

                for (Invite object : objects) {

                    objectsIds.add(object.getOwnerUserId());

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

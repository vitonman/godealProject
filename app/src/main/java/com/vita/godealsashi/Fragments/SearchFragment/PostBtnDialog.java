package com.vita.godealsashi.Fragments.SearchFragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.vita.godealsashi.R;

public class PostBtnDialog extends DialogFragment {

    private static final String TAG = "MyCustomDialog";

    //widjecs
    private EditText editPostName;
    private TextView mActionOk, mActionCancel;
    //private EditText descriptionText;

    public OnInputSelected mOnInputSelected;

    public interface OnInputSelected{

        void sendInput(String postname, String city);

    }





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.add_post_dialog, container, false);

        mActionOk = v.findViewById(R.id.action_ok);
        mActionCancel = v.findViewById(R.id.cancel_btn);
        editPostName = v.findViewById(R.id.post_name_edit);

        //get the spinner from the xml.
        final Spinner dropdown = v.findViewById(R.id.spinner_work_post);
        //create a list of items for the spinner.
        String[] items = new String[]{"Tallinn", "Tartu", "Pärnu", "Rakvere"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        dropdown.setAdapter(adapter);



        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: close dialogs");
                getDialog().dismiss();
            }
        });
        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: capturing input");

                String postname = editPostName.getText().toString();
                String city = dropdown.getSelectedItem().toString();
                if(!postname.equals("")){

                    //

                    mOnInputSelected.sendInput(postname, city);


                }
                getDialog().dismiss();
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{

            mOnInputSelected = (OnInputSelected) getTargetFragment();

        }catch(ClassCastException e){

            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());


        }
    }
}

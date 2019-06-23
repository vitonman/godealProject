package com.vita.godealsashi.registration;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vita.godealsashi.R;

public class RegistrationComplete extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private SliderAdapter sliderAdapter;

    private TextView [] mDots;

    private LinearLayout linear_setup_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_complete);

        linear_setup_layout = (LinearLayout) findViewById(R.id.linear_setup_layout);
        mSlideViewPager = (ViewPager) findViewById(R.id.view_pager);

        sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);


        mSlideViewPager.addOnPageChangeListener(viewListner);
    }


    public void addDotsIndicator(int position){

        linear_setup_layout.removeAllViews();

        mDots = new TextView[3];


        for (int i = 0; i < mDots.length; i++) {


            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.black));


            linear_setup_layout.addView(mDots[i]);
        }

        if(mDots.length > 0){

            mDots[position].setTextColor(getResources().getColor(R.color.white));

        }

    }


    ViewPager.OnPageChangeListener viewListner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

            addDotsIndicator(i);

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    public void sendToSetup(){

        Intent mainIntent = new Intent(RegistrationComplete.this, UserSetupActivity.class);
        startActivity(mainIntent);
        finish();

    }


}

package com.vita.godealsashi.registration;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vita.godealsashi.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){

        this.context = context;

    }

    public int[] slide_images = {


            R.drawable.archive,
            R.drawable.archive_one,
            R.drawable.archivetwo

    };

    public String[] slide_headings = {

      "Registration",
      "Some data",
      "Finish page"

    };

    public String[] desc = {

            "Hello there is some information about page",
            "There are some extra information about you",
            "This is final page of your page setup"

    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (ConstraintLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.user_setup_second_slide, container, false);

        ImageView slideView = (ImageView) view.findViewById(R.id.image_draw);
        TextView heading = (TextView) view.findViewById(R.id.heading);
        TextView text = (TextView) view.findViewById(R.id.some_text);


        slideView.setImageResource(slide_images[position]);
        heading.setText(slide_headings[position]);
        text.setText(desc[position]);

        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}

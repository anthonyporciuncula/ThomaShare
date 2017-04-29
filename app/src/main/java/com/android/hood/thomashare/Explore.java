package com.android.hood.thomashare;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.RelativeLayout;

import com.android.hood.thomashare.Adapters.ExploreAdapter;

public class Explore extends AppCompatActivity {

    private Context mContext;

    RelativeLayout mRelativeLayout;
    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        // Get the application context
        mContext = getApplicationContext();

        // Get the widgets reference from XML layout
        mRelativeLayout = (RelativeLayout) findViewById(R.id.activity_explore);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_explore);

        // Initialize a new String array
        String[] courses = {
                "IT",
                "CFAD",
                "ENG",
                "MED",
                "HRM",
                "EDUC",
                "ARCHI",
                "AB",
                "PHIL",
                "LAW",
                "MSC"
        };

        /*
            GridLayoutManager
                A RecyclerView.LayoutManager implementations that lays out items in a grid.
                By default, each item occupies 1 span. You can change it by providing a custom
                GridLayoutManager.SpanSizeLookup instance via setSpanSizeLookup(SpanSizeLookup).
        */
        /*
            public GridLayoutManager (Context context, int spanCount)
                Creates a vertical GridLayoutManager

            Parameters
                context : Current context, will be used to access resources.
                spanCount : The number of columns in the grid
        */
        // Define a layout for RecyclerView
        mLayoutManager = new GridLayoutManager(mContext,3);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Initialize a new instance of RecyclerView Adapter instance
        mAdapter = new ExploreAdapter(mContext,courses);

        // Set the adapter for RecyclerView
        mRecyclerView.setAdapter(mAdapter);
    }
}

package com.android.hood.thomashare.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hood.thomashare.Explore;
import com.android.hood.thomashare.Explore_fileList;
import com.android.hood.thomashare.R;

import java.util.Random;


public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ViewHolder>{
    private String[] mDataSet;
    private Context mContext;
    private Random mRandom = new Random();

    public ExploreAdapter(Context context, String[] DataSet){
        mDataSet = DataSet;
        mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;
        public Button mButton;
        public LinearLayout mLinearLayout;
        public ViewHolder(View v){

            super(v);
            mButton = (Button) v.findViewById(R.id.btn_explore);

//            mTextView = (TextView) v.findViewById(R.id.tv);
            mLinearLayout = (LinearLayout) v.findViewById(R.id.ll);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Create a new View
        View v = LayoutInflater.from(mContext).inflate(R.layout.grid_view_explore,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        holder.mButton.setText(mDataSet[position]);
        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext,mDataSet[position] + " IS CLICKED", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent = new Intent(mContext, Explore_fileList.class);
                intent.putExtra("course", mDataSet[position]);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
//        holder.mTextView.setText(mDataSet[position]);
        // Generate a random color
        int color = getRandomHSVColor();

        // Set a random color for TextView background
//        holder.mTextView.setBackgroundColor(getLighterColor(color));

        // Set a text color for TextView
//        holder.mButton.setTextColor(Color.parseColor("#000000"));
//        holder.mTextView.setTextColor(Color.parseColor("#000000"));

        // Set a gradient background for LinearLayout
        holder.mLinearLayout.setBackgroundColor(getLighterColor(color));
//        holder.mLinearLayout.setBackground(getGradientDrawable());

        // Emboss the TextView text
//        applyEmbossMaskFilter(holder.mTextView);
    }

    @Override
    public int getItemCount(){
        return mDataSet.length;
    }

    // Custom method to apply emboss mask filter to TextView
    protected void applyEmbossMaskFilter(TextView tv){
        EmbossMaskFilter embossFilter = new EmbossMaskFilter(
                new float[]{1f, 5f, 1f}, // direction of the light source
                0.8f, // ambient light between 0 to 1
                8, // specular highlights
                7f // blur before applying lighting
        );
        tv.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        tv.getPaint().setMaskFilter(embossFilter);
    }

    // Custom method to generate random HSV color
    protected int getRandomHSVColor(){
        // Generate a random hue value between 0 to 360
        int hue = mRandom.nextInt(361);
        // We make the color depth full
        float saturation = 1.0f;
        // We make a full bright color
        float value = 1.0f;
        // We avoid color transparency
        int alpha = 255;
        // Finally, generate the color
        int color = Color.HSVToColor(alpha, new float[]{hue, saturation, value});
        // Return the color
        return color;
    }

    // Custom method to create a GradientDrawable object
    protected GradientDrawable getGradientDrawable(){
        GradientDrawable gradient = new GradientDrawable();
        gradient.setGradientType(GradientDrawable.SWEEP_GRADIENT);
        gradient.setColors(new int[]{getRandomHSVColor(), getRandomHSVColor(),getRandomHSVColor()});
        return gradient;
    }

    // Custom method to get a darker color
    protected int getDarkerColor(int color){
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] = 0.8f *hsv[2];
        return Color.HSVToColor(hsv);
    }

    // Custom method to get a lighter color
    protected int getLighterColor(int color){
        float[] hsv = new float[3];
        Color.colorToHSV(color,hsv);
        hsv[2] = 0.2f + 0.8f * hsv[2];
        return Color.HSVToColor(hsv);
    }

    // Custom method to get reverse color
    protected int getReverseColor(int color){
        float[] hsv = new float[3];
        Color.RGBToHSV(
                Color.red(color), // Red value
                Color.green(color), // Green value
                Color.blue(color), // Blue value
                hsv
        );
        hsv[0] = (hsv[0] + 180) % 360;
        return Color.HSVToColor(hsv);
    }
}
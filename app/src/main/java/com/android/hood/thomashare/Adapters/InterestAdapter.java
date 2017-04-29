package com.android.hood.thomashare.Adapters;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.hood.thomashare.Bean.Interest;
import com.android.hood.thomashare.R;

import java.util.List;

public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.MyViewHolder> {

    private List<Interest> interestList;

    /**
     * View holder class
     * */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView interestText;

        public MyViewHolder(View view) {
           super(view);
           interestText = (TextView) view.findViewById(R.id.tv_interests);
        }
    }

    public InterestAdapter(List<Interest> interestList) {
        this.interestList = interestList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        System.out.println("Bind ["+holder+"] - Pos ["+position+"]");
        Interest c = interestList.get(position);
        holder.interestText.setText(c.interest);
    }

    @Override
    public int getItemCount() {
        Log.d("RV", "Item size ["+ interestList.size()+"]");
       return interestList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent, false);
        return new MyViewHolder(v);
    }
}

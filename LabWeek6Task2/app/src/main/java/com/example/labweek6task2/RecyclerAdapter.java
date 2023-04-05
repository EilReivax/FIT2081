package com.example.labweek6task2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    public static final String TAG = "WEEK_6_TAG";
    ArrayList<Fruit> fruits = new ArrayList<>();

    public void setFruits(ArrayList<Fruit> fruits) {
        this.fruits = fruits;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder");

        holder.viewName.setText(fruits.get(position).getName());
        holder.viewFamily.setText(fruits.get(position).getFamily());
        holder.viewCalories.setText(fruits.get(position).getCalories());
        holder.viewSugar.setText(fruits.get(position).getSugar());
        holder.viewCarbohydrates.setText(fruits.get(position).getCarbohydrates());
    }

    @Override
    public int getItemCount() {
        return fruits.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView viewName;
        public TextView viewFamily;
        public TextView viewCalories;
        public TextView viewSugar;
        public TextView viewCarbohydrates;

        public ViewHolder(@NonNull View view) {
            super(view);
            viewName = view.findViewById(R.id.viewName);
            viewFamily = view.findViewById(R.id.viewFamily);
            viewCalories = view.findViewById(R.id.viewCalories);
            viewSugar = view.findViewById(R.id.viewSugar);
            viewCarbohydrates = view.findViewById(R.id.viewCarbohydrates);
        }
    }
}

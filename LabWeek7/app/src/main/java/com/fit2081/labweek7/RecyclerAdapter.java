package com.fit2081.labweek7;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    public static final String TAG = "WEEK_3_TAG";
    ArrayList<Book> books = new ArrayList<>();

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
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

        holder.viewId.setText(books.get(position).getId());
        holder.viewTitle.setText(books.get(position).getTitle());
        holder.viewIsbn.setText(books.get(position).getIsbn());
        holder.viewAuthor.setText(books.get(position).getAuthor());
        holder.viewDescription.setText(books.get(position).getDescription());
        holder.viewPrice.setText(books.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView viewId;
        public TextView viewTitle;
        public TextView viewIsbn;
        public TextView viewAuthor;
        public TextView viewDescription;
        public TextView viewPrice;

        public ViewHolder(@NonNull View view) {
            super(view);
            viewId = view.findViewById(R.id.viewId);
            viewTitle = view.findViewById(R.id.viewTitle);
            viewIsbn = view.findViewById(R.id.viewIsbn);
            viewAuthor = view.findViewById(R.id.viewAuthor);
            viewDescription = view.findViewById(R.id.viewDescription);
            viewPrice = view.findViewById(R.id.viewPrice);
        }
    }
}

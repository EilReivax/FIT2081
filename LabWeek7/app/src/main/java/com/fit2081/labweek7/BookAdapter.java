package com.fit2081.labweek7;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fit2081.labweek7.provider.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> mBook;

    public BookAdapter() {}

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);

        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.viewId.setText(String.valueOf(mBook.get(position).getId()));
        holder.viewTitle.setText(mBook.get(position).getTitle());
        holder.viewIsbn.setText(mBook.get(position).getIsbn());
        holder.viewAuthor.setText(mBook.get(position).getAuthor());
        holder.viewDescription.setText(mBook.get(position).getDescription());
        holder.viewPrice.setText(mBook.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        if (mBook == null) {
            return 0;
        }
        else {
            return mBook.size();
        }
    }

    public void setBook(List<Book> newData) {
        mBook = newData;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView viewId;
        public TextView viewTitle;
        public TextView viewIsbn;
        public TextView viewAuthor;
        public TextView viewDescription;
        public TextView viewPrice;

        public BookViewHolder(@NonNull View view) {
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

package com.fit2081.labweek7.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class BookViewModel extends AndroidViewModel  {
    private BookRepository mRepository;
    private LiveData<List<Book>> mAllBook;

    public BookViewModel(@NonNull Application application) {
        super(application);
        mRepository = new BookRepository(application);
        mAllBook = mRepository.getAllBook();
    }

    public LiveData<List<Book>> getAllBook() {
        return mAllBook;
    }

    public void insert(Book book) {
        mRepository.insert(book);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void deleteLast() {
        mRepository.deleteLast();
    }
}

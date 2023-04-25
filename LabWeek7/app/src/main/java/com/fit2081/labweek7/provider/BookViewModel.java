package com.fit2081.labweek7.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class BookViewModel extends AndroidViewModel  {
    private BookRepository bookRepository;
    private LiveData<List<Book>> books;

    public BookViewModel(@NonNull Application application) {
        super(application);
        bookRepository = new BookRepository(application);
        books = bookRepository.getAllBook();
    }

    public LiveData<List<Book>> getAllBook() {
        return books;
    }

    public void insert(Book book) {
        bookRepository.insert(book);
    }

    public void deleteAll() {
        bookRepository.deleteAll();
    }

    public void deleteLast() {
        bookRepository.deleteLast();
    }
}

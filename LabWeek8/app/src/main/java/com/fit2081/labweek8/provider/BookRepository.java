package com.fit2081.labweek8.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class BookRepository {
    private BookDao bookDao;
    private LiveData<List<Book>> books;

    BookRepository(Application application) {
        BookDatabase db = BookDatabase.getDatabase(application);
        bookDao = db.bookDao();
        books = bookDao.getAllBook();
    }

    LiveData<List<Book>> getAllBook() {
        return books;
    }

    void insert(Book book) {
        BookDatabase.databaseWriteExecutor.execute(() -> bookDao.addBook(book));
    }

    void deleteAll() {
        BookDatabase.databaseWriteExecutor.execute(() -> bookDao.deleteAll());
    }

    void deleteLast() {
        BookDatabase.databaseWriteExecutor.execute(() -> bookDao.deleteLast());
    }
}

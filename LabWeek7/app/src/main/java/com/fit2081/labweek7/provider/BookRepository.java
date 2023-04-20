package com.fit2081.labweek7.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class BookRepository {
    private BookDao mBookDao;
    private LiveData<List<Book>> mAllBook;

    BookRepository(Application application) {
        BookDatabase db = BookDatabase.getDatabase(application);
        mBookDao = db.bookDao();
        mAllBook = mBookDao.getAllBook();
    }

    LiveData<List<Book>> getAllBook() {
        return mAllBook;
    }

    void insert(Book book) {
        BookDatabase.databaseWriteExecutor.execute(() -> mBookDao.addBook(book));
    }

    void deleteAll() {
        BookDatabase.databaseWriteExecutor.execute(() -> mBookDao.deleteAll());
    }

    void deleteLast() {
        BookDatabase.databaseWriteExecutor.execute(() -> mBookDao.deleteLast());
    }
}

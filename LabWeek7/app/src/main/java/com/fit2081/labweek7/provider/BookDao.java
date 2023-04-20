package com.fit2081.labweek7.provider;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao public interface BookDao {
    @Query("select * from books")
    LiveData<List<Book>> getAllBook();

    @Insert
    void addBook(Book book);

    @Query("delete from books")
    void deleteAll();

    @Query("delete from books where id = (select max(id) from books)")
    void deleteLast();
}

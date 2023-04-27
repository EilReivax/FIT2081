package com.fit2081.labweek9.provider;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "books")
public class Book {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id", defaultValue = "0")
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "isbn")
    private String isbn;

    @ColumnInfo(name = "author")
    private String author;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "price")
    private String price;

    public Book(String title, String isbn, String author, String description, String price) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.description = description;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }
}
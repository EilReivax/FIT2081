package com.fit2081.labweek7;

public class Book {
    private String id, title, isbn, author, description, price;

    public Book(String id, String title, String isbn, String author, String description, String price) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.description = description;
        this.price = price;
    }

    public String getId() {
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
}
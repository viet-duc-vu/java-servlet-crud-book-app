package com.ducvu.bookstore;

/**
 * Book.java
 * This is a model class that represents a Book entity
 * @author ducvu
 */
public class Book {
    protected int book_id;
    protected String title;
    protected String author;
    protected double price;

    public Book(int book_id) {
        this.book_id = book_id;
    }

    public Book(int book_id, String title, String author, double price) {
        this(title, author, price);
        this.book_id = book_id;
    }

    public Book(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

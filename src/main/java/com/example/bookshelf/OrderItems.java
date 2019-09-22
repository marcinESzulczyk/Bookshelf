package com.example.bookshelf;

import java.util.List;

public class OrderItems {
    private long orgerItemID;
    private Order order;
    private List<Book> books;

    public long getOrgerItemID() {
        return orgerItemID;
    }

    public void setOrgerItemID(long orgerItemID) {
        this.orgerItemID = orgerItemID;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}

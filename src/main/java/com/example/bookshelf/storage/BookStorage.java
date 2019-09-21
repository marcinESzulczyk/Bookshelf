package com.example.bookshelf.storage;

import com.example.bookshelf.Book;

import java.sql.SQLException;
import java.util.List;



public interface BookStorage {
    Book getBook(long id);
    List<Book> getAllBooks();
    void addBook(Book book);
    List<Book> getBookStorage();
}



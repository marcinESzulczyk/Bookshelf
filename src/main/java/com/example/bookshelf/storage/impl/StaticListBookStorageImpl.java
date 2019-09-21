package com.example.bookshelf.storage.impl;

import com.example.bookshelf.Book;
import com.example.bookshelf.storage.BookStorage;

import java.util.ArrayList;
import java.util.List;

public class StaticListBookStorageImpl implements BookStorage {

    private static List<Book> bookStorage = new ArrayList<Book>();

    public List<Book> getBookStorage() {
        return bookStorage;
    }

    public Book getBook(long id){
        for (Book book:bookStorage){
            if (book.getId() == id){
                return book;
            }
        }
        return null;
    }

    public List<Book> getAllBooks(){
        return bookStorage;
    }

    public void addBook (Book book){
        bookStorage.add(book);
    }



}

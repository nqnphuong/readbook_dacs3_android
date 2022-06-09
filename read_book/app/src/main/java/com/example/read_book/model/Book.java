package com.example.read_book.model;

import java.util.List;

public class Book {
    private int id_book, id_category, id_user;
    private String bookName, bookImage, bookAuthor, bookDescription, bookStatus, bookNumberLove;
    private int bookNumbook;


    public Book(int id_book, String bookName, String bookImage, String bookAuthor, String bookDescription, String bookStatus, int bookNumbook) {
        this.id_book = id_book;
        this.bookName = bookName;
        this.bookImage = bookImage;
        this.bookAuthor = bookAuthor;
        this.bookDescription = bookDescription;
        this.bookStatus = bookStatus;
        this.bookNumbook = bookNumbook;
    }

    public Book(String bookImage, String bookName, String bookAuthor, int id_category) {
        this.bookImage = bookImage;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.id_category = id_category;
    }

    public Book(int id_book, String bookImage, String bookName, String bookAuthor, String bookDescription, int id_category) {
        this.id_book = id_book;
        this.bookImage = bookImage;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookDescription = bookDescription;
        this.id_category = id_category;
    }

    public Book(int id_book, String bookImage, int id_user, String bookName,  String bookAuthor) {
        this.id_book = id_book;
        this.bookImage = bookImage;
        this.id_user = id_user;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
    }

    public int getBookNumbook() {
        return bookNumbook;
    }

    public void setBookNumbook(int bookNumbook) {
        this.bookNumbook = bookNumbook;
    }

    public int getId_book() {
        return id_book;
    }

    public void setId_book(int id_book) {
        this.id_book = id_book;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }

    public String getBookNumberLove() {
        return bookNumberLove;
    }

    public void setBookNumberLove(String bookNumberLove) {
        this.bookNumberLove = bookNumberLove;
    }
}

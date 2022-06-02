package com.example.read_book.model;

public class Library {
    private Integer id_library, id_user, id_book;
    private String libraryStatusread;

    public Library(Integer id_library, Integer id_user, Integer id_book, String libraryStatusread) {
        this.id_library = id_library;
        this.id_user = id_user;
        this.id_book = id_book;
        this.libraryStatusread = libraryStatusread;
    }

    public Integer getId_library() {
        return id_library;
    }

    public void setId_library(Integer id_library) {
        this.id_library = id_library;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public Integer getId_book() {
        return id_book;
    }

    public void setId_book(Integer id_book) {
        this.id_book = id_book;
    }

    public String getLibraryStatusread() {
        return libraryStatusread;
    }

    public void setLibraryStatusread(String libraryStatusread) {
        this.libraryStatusread = libraryStatusread;
    }
}

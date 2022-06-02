package com.example.read_book.model;

public class Category_book {
    private int id_book, id_category, id_category_book;

    public Category_book(int id_book, int id_category, int id_category_book) {
        this.id_book = id_book;
        this.id_category = id_category;
        this.id_category_book = id_category_book;
    }

    public int getId_book() {
        return id_book;
    }

    public void setId_book(int id_book) {
        this.id_book = id_book;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public int getId_category_book() {
        return id_category_book;
    }

    public void setId_category_book(int id_category_book) {
        this.id_category_book = id_category_book;
    }
}

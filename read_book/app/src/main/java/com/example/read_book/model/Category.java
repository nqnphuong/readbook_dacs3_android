package com.example.read_book.model;

import java.util.List;

public class Category {
    private String categoryImage, categoryName;
    private int id_category;
    private int id_book;
    private int category_choose_color;


    public Category(String categoryName, int id_category, int id_book, int category_choose_color) {
        this.categoryName = categoryName;
        this.id_category = id_category;
        this.id_book = id_book;
        this.category_choose_color = category_choose_color;
    }

    public Category(String categoryName, int id_category) {
        this.categoryName = categoryName;
        this.id_category = id_category;
    }

    public int getCategory_choose_color() {
        return category_choose_color;
    }

    public void setCategory_choose_color(int category_choose_color) {
        this.category_choose_color = category_choose_color;
    }

    public int getId_book() {
        return id_book;
    }

    public void setId_book(int id_book) {
        this.id_book = id_book;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }


}

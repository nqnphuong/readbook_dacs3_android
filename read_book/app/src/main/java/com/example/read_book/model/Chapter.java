package com.example.read_book.model;

public class Chapter {
//    chapter = Chapter(id_book,chapterTitle, chapterContent, chapterNumberLove)
    private Integer id_chapter, id_book, chapterNumberLove;
    private String chapterTitle, chapterContent;

    public Chapter(Integer id_chapter, String chapterTitle) {
        this.id_chapter = id_chapter;
        this.chapterTitle = chapterTitle;
    }

    public Chapter(Integer id_chapter, Integer id_book, String chapterTitle) {
        this.id_chapter = id_chapter;
        this.id_book = id_book;
        this.chapterTitle = chapterTitle;
    }

    public Chapter(Integer id_chapter, Integer id_book, Integer chapterNumberLove, String chapterTitle, String chapterContent) {
        this.id_chapter = id_chapter;
        this.id_book = id_book;
        this.chapterNumberLove = chapterNumberLove;
        this.chapterTitle = chapterTitle;
        this.chapterContent = chapterContent;
    }

    public Integer getId_chapter() {
        return id_chapter;
    }

    public void setId_chapter(Integer id_chapter) {
        this.id_chapter = id_chapter;
    }

    public Integer getId_book() {
        return id_book;
    }

    public void setId_book(Integer id_book) {
        this.id_book = id_book;
    }

    public Integer getChapterNumberLove() {
        return chapterNumberLove;
    }

    public void setChapterNumberLove(Integer chapterNumberLove) {
        this.chapterNumberLove = chapterNumberLove;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public String getChapterContent() {
        return chapterContent;
    }

    public void setChapterContent(String chapterContent) {
        this.chapterContent = chapterContent;
    }
}

package com.example.cctv;

public class BoardList {
    private int rank;             //게시글 순위
    private String title;         // 게시글 제목
    private String description;    // 게시글 내용

    public int getRank() {
        return rank;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public BoardList(int rank, String title, String description) {
        this.rank = rank;
        this.title = title;
        this.description = description;
    }

}

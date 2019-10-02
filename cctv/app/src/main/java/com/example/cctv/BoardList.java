package com.example.cctv;

public class BoardList {
    private int rank;             //게시글 순위
    private String title;         // 게시글 제목
    private String description;    // 게시글 내용
    private int comment_count;    // 댓글 개수

    public int getRank() {
        return rank;
    }
    public void setRank(int rank){this.rank =rank;}

    public String getTitle() {
        return title;
    }
    public void setTitle(String title){this.title =title;}

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getComment_count() {
        return comment_count;
    }
    public void setComment_count(int comment_count){this.comment_count =comment_count;}
}

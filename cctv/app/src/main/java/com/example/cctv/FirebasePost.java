package com.example.cctv;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FirebasePost {

    public long getP_id() {
        return p_id;
    }

    public void setP_id(long p_id) {
        this.p_id = p_id;
    }

    public String getP_text() {
        return p_text;
    }

    public void setP_text(String p_text) {
        this.p_text = p_text;
    }

    public String getP_title() {
        return p_title;
    }

    public void setP_title(String p_title) {
        this.p_title = p_title;
    }

    public long getGood() {
        return p_good;
    }

    public void setGood(int good) {
        this.p_good = good;
    }

    public String getP_writer() {return p_writer;}

    public void setP_writer(String p_writer) {this.p_writer = p_writer;}


    private long p_id;
    private String p_text;
    private String p_title;
    private int p_good;
    private String p_writer;
    public FirebasePost(){

    }
    public FirebasePost(long id ,String title, String text,int good,String writer){
        this.p_id = id;
        this.p_text = text;
        this.p_title = title;
        this.p_good = good;
        this.p_writer = writer;
    }

    @Exclude
    public Map<String , Object> toMap(){
        HashMap<String , Object> result = new HashMap<>();
        result.put("p_id",p_id);
        result.put("p_title",p_title);
        result.put("p_text",p_text);
        result.put("p_good",p_good);
        result.put("p_writer",p_writer);
        return result;
    }

}

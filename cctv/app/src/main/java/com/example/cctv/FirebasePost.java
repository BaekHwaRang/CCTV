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

    private long p_id;

    public long getC_id() {
        return c_id;
    }

    public void setC_id(long c_id) {
        this.c_id = c_id;
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

    public String getC_text() {
        return c_text;
    }

    public void setC_text(String c_text) {
        this.c_text = c_text;
    }

    public long getGood() {
        return p_good;
    }

    public void setGood(int good) {
        this.p_good = good;
    }

    private long c_id;

    private String p_text;
    private String p_title;
    private String c_text;
    private int p_good;
    public FirebasePost(){

    }
    public FirebasePost(long id ,String title, String text,int good){
        this.p_id = id;
        this.p_text = text;
        this.p_title = title;
        this.p_good = good;
    }
    @Exclude
    public Map<String , Object> toMap(){
        HashMap<String , Object> result = new HashMap<>();
        result.put("p_id",p_id);
        result.put("p_title",p_title);
        result.put("p_text",p_text);
        result.put("p_good",p_good);
        return result;
    }

}

package com.example.cctv;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FirebasePost {

    private long id;
    private String text;
    private String title;
    public FirebasePost(){

    }
    public FirebasePost(long id ,String title, String text){
        this.id = id;
        this.text = text;
        this.title = title;
    }
@Exclude
    public Map<String , Object> toMap(){
        HashMap<String , Object> result = new HashMap<>();
        result.put("post_index",id);
        result.put("post_title",title);
        result.put("post_text",text);
        return result;
    }
}

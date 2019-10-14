package com.example.cctv;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FirebasePost {
    private String id;
    private String text;

    public FirebasePost(String id , String text){
        this.id = id;
        this.text = text;
    }
@Exclude
    public Map<String , Object> toMap(){
        HashMap<String , Object> result = new HashMap<>();
        result.put("id",id);
        result.put("text",text);
        return result;
    }
}

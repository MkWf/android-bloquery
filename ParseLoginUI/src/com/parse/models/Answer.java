package com.parse.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Mark on 2/28/2015.
 */

@ParseClassName("Answer")
public class Answer extends ParseObject {
    String parent;
    String answer;

    public Answer(){}

    public Answer(String answer, String parent, String user){
        setAnswer(answer);
        setParent(parent);
        setParentUser(user);
    }

    public String getAnswer() {
        return getString("answer");
    }

    public String getParent(){
        return getString("parent");
    }

    public String getParentUser() {
        return getString("parentUser");
    }

    public void setAnswer(String answer) {
        put("answer", answer);
    }

    public void setParent(String question){
        put("parent", question);
    }

    public void setParentUser(String user) { put("parentUser", user);}




}

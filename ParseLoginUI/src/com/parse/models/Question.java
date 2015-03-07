package com.parse.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Mark on 2/26/2015.
 */

@ParseClassName("Question")
public class Question extends ParseObject {

    public Question(String question){
        setQuestion(question);
        setAnswers(0);
    }

    public String getQuestion() {
        return getString("question");
    }

    public void setQuestion(String question) {
        put("question", question);
    }

    public int getAnswers(){
        return getInt("answers");
    }

    //public int getRating() {return rating;}
    //public void setUser(BloQueryUser user){this.user = user;}
    //public BloQueryUser getUser(){return user;}
    ////public void setRating(int rating) {this.rating = rating;}

    public String getParent(){
        return getString("parent");
    }

    public void setParent(String user){
        put("parent", user);
    }

    public void setAnswers(int num){
        put("answers", num);
    }



    public Question(){}
}

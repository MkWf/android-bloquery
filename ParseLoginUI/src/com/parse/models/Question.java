package com.parse.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Mark on 2/26/2015.
 */

@ParseClassName("Question")
public class Question extends ParseObject {

    String question;
    BloQueryUser user;
    int rating;

    public Question(){
        question = "";
        user = null;
        rating = 0;
    }

    public BloQueryUser getUser(){return user;}

    public String getQuestion() {return question;}

    public int getRating() {return rating;}

    public void setUser(BloQueryUser user){this.user = user;}

    public void setQuestion(String question) {this.question = question;}

    public void setRating(int rating) {this.rating = rating;}
}

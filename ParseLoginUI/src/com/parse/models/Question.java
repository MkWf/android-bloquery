package com.parse.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Mark on 2/26/2015.
 */

@ParseClassName("Question")
public class Question extends ParseObject {

    public Question(String question, ParseUser questionOwner){
        setQuestion(question);
        setAnswers(0);
        setQuestionOwner(questionOwner);
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

    public ParseUser getQuestionOwner()  {
        return getParseUser("questionOwner");
    }

    public void setQuestionOwner(ParseUser user){
        put("questionOwner", user);
    }

    public void setAnswers(int num){
        put("answers", num);
    }



    public Question(){}
}

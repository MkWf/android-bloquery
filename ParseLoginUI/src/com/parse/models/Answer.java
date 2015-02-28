package com.parse.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Mark on 2/28/2015.
 */

@ParseClassName("Answer")
public class Answer extends ParseObject {
    int question;
    String answer;

    public Answer(){

    }

    public Answer(String answer){
        this.answer = answer;
    }

    public void setAnswer(String answer){
        this.answer = answer;
    }

    public String getAnswer(){
        return answer;
    }
}

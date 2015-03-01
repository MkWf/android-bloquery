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

    public Answer(String answer, String parent){
        this.answer = answer;
        this.parent = parent;
    }

    public void setAnswer(String answer){this.answer = answer;}

    public void setParentQuestion(String question){this.parent = question;}

    public String getAnswer(){return answer;}
}

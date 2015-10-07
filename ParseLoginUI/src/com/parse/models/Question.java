package com.parse.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Mark on 2/26/2015.
 */

@ParseClassName("Question")
public class Question extends ParseObject {

    public static String QUESTION = "question";
    public static String QUESTION_OWNER = "questionOwner";
    public static String ANSWERS = "answers";

    public Question(String question, ParseUser questionOwner){
        setQuestion(question);
        setAnswers(0);
        setQuestionOwner(questionOwner);
    }

    public String getQuestion() {
        return getString(QUESTION);
    }

    public void setQuestion(String question) {
        put(QUESTION, question);
    }

    public int getAnswers(){
        return getInt(ANSWERS);
    }

    public ParseUser getQuestionOwner()  {
        return getParseUser(QUESTION_OWNER);
    }

    public void setQuestionOwner(ParseUser user){
        put(QUESTION_OWNER, user);
    }

    public void setAnswers(int num){
        put(ANSWERS, num);
    }

    public Question(){}
}

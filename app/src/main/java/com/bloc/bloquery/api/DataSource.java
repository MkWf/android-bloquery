package com.bloc.bloquery.api;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.parse.models.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 2/27/2015.
 */
public class DataSource {
    private List<Question> questions;
    private List<Question> answers;

    public DataSource() {
        questions = new ArrayList<Question>();
        //answers = new ArrayList<Question>();
        createFakeQuestions();
        //createFakeAnswers();
    }

    private void createFakeAnswers() {
        for(int i=0; i<10; i++){
            answers.add(new Question());
        }
    }

    public List<Question> getAnswers() {
        return answers;
    }
    public List<Question> getQuestions() {
        return questions;
    }

    void createFakeQuestions() {
        Question p = new Question();
        p.setQuestion("Question 1");
        p.saveInBackground();

        Question q = new Question();
        q.setQuestion("Question 2");
        q.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                ParseQuery<Question> query = ParseQuery.getQuery("Question");
                query.orderByDescending("createdAt");
                query.setLimit(10);
                query.findInBackground(new FindCallback<Question>() {
                    public void done(List<Question> questionsList, ParseException e) {
                        questions = questionsList;
                    }
                });
            }
        });
    }
}


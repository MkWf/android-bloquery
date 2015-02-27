package com.bloc.bloquery.api;

import com.parse.models.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 2/27/2015.
 */
public class DataSource {
    private List<Question> questions;

    public DataSource() {
        questions = new ArrayList<Question>();
        createFakeData();
    }

    public List<Question> getQuestions() {
        return questions;
    }

    void createFakeData() {
        for(int i=0; i<10; i++){
            questions.add(new Question());
        }
    }
}


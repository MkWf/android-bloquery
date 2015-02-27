package com.bloc.bloquery.api;

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
        createFakeQuestions();
        createFakeAnswers();
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
        for(int i=0; i<10; i++){
            questions.add(new Question(i));
        }
    }
}


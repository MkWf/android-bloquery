package com.bloc.bloquery.api;

import android.os.Handler;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.models.Answer;
import com.parse.models.BloQueryUser;
import com.parse.models.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Mark on 2/27/2015.
 */
public class DataSource {
    public static interface Callback {
        public void onSuccess();
        public void onError(String errorMessage);
    }

    private ExecutorService executorService;

    void submitTask(Runnable task) {
        if (executorService.isShutdown() || executorService.isTerminated()) {
            executorService = Executors.newSingleThreadExecutor();
        }
        executorService.submit(task);
    }

    private List<Question> questions;
    private List<Answer> answers;
    private BloQueryUser currentUser;
    private BloQueryUser viewedUser;

    public DataSource() {
        executorService = Executors.newSingleThreadExecutor();
        questions = new ArrayList<Question>();
        answers = new ArrayList<Answer>();
        fetchQuestions();
    }

    public List<Answer> getAnswers() {
        return answers;
    }
    public List<Question> getQuestions() {
        return questions;
    }
    public void setCurrentUser(BloQueryUser user){
        this.currentUser = (BloQueryUser)user;
    }
    public BloQueryUser getCurrentUser(){
        return this.currentUser;
    }
    public void setCurrentViewedUser(BloQueryUser user){
        this.viewedUser = (BloQueryUser)user;
    }
    public BloQueryUser getCurrentViewedUser(){
        return this.viewedUser;
    }
    public void setQuestions(List<Question> questions){
        this.questions = questions;
    }
    public void setAnswers(List<Answer> answers) { this.answers = answers; }

    void fetchQuestions() {
        ParseQuery<Question> query = ParseQuery.getQuery("Question");
        query.orderByDescending("createdAt");
        query.setLimit(10);
        query.findInBackground(new FindCallback<Question>() {
            public void done(List<Question> questionsList, ParseException e) {
                questions = questionsList;
            }
        });
    }

    public void fetchAnswers(final Question question, final Callback result){
        final Handler callbackThreadHandler = new Handler();
        submitTask(new Runnable() {
            @Override
            public void run() {
                ParseQuery<Answer> query = ParseQuery.getQuery("Answer");
                query.whereEqualTo("question", question);
                query.orderByDescending("createdAt");
                query.setLimit(10);
                query.findInBackground(new FindCallback<Answer>() {
                    public void done(List<Answer> answersList, ParseException e) {
                        answers = answersList;
                        callbackThreadHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                result.onSuccess();
                            }
                        });
                    }
                });
            }
        });
    }
}


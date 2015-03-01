package com.bloc.bloquery.api;

import android.os.Handler;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.models.Answer;
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

    public DataSource() {
        executorService = Executors.newSingleThreadExecutor();
        questions = new ArrayList<Question>();
        answers = new ArrayList<Answer>();
        createFakeQuestions();
        //createFakeAnswers();
    }

    public List<Answer> getAnswers() {
        return answers;
    }
    public List<Question> getQuestions() {
        return questions;
    }

    void createFakeQuestions() {
       //Question p = new Question();
        //p.setQuestion("What is 2+2?");
       // p.saveInBackground();

       // Question q = new Question();
        //q.setQuestion("Question 2");
        //q.saveInBackground(new SaveCallback() {
        //    @Override
         //   public void done(ParseException e) {
                ParseQuery<Question> query = ParseQuery.getQuery("Question");
                query.orderByDescending("createdAt");
                query.setLimit(10);
                query.findInBackground(new FindCallback<Question>() {
                    public void done(List<Question> questionsList, ParseException e) {
                        questions = questionsList;
                    }
                });
            //}
       // });
    }

    public void fetchAnswers(final String questionId, final Callback result){
        final Handler callbackThreadHandler = new Handler();
        submitTask(new Runnable() {
            @Override
            public void run() {
                ParseQuery<Answer> query = ParseQuery.getQuery("Answer");
                query.whereEqualTo("parent", questionId);
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


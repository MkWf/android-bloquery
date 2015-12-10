package com.bloc.bloquery.ui.activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.bloc.bloquery.BloQueryApplication;
import com.bloc.bloquery.R;
import com.bloc.bloquery.adapters.QuestionItemAdapter;
import com.bloc.bloquery.api.DataSource;
import com.bloc.bloquery.ui.fragments.AnswersDialogFragment;
import com.bloc.bloquery.ui.fragments.AnswersFragment;
import com.bloc.bloquery.ui.fragments.ProfileFragment;
import com.bloc.bloquery.ui.fragments.ProfileViewFragment;
import com.bloc.bloquery.ui.fragments.QuestionsDialogFragment;
import com.bloc.bloquery.ui.fragments.QuestionsFragment;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.models.Answer;
import com.parse.models.BloQueryUser;
import com.parse.models.Question;
import com.parse.ui.ParseLoginBuilder;

import java.util.List;

/**
 * Created by Mark on 2/25/2015.
 */
public class MainActivity extends ActionBarActivity implements QuestionsFragment.Delegate,
        QuestionsDialogFragment.SubmitQuestionDialogListener,
        AnswersDialogFragment.SubmitAnswerDialogListener {

    Fragment currentFragment;
    Toolbar toolbar;
    Question clickedItem;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tb_activity_main);
        toolbar.setLogo(R.mipmap.ic_toolbar_icon);
        setSupportActionBar(toolbar);

        ParseLoginBuilder builder = new ParseLoginBuilder(MainActivity.this);
        startActivityForResult(builder.build(), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 0){
            finish();
        }else {
            BloQueryApplication.getSharedDataSource().setCurrentUser((BloQueryUser) BloQueryUser.getCurrentUser());

            QuestionsFragment qf = new QuestionsFragment();
            currentFragment = qf;
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_activity_main, qf, "Question")
                    .commit();
        }
    }

    @Override
    public void onItemClicked(QuestionItemAdapter itemAdapter, final Question questionItem) {
        clickedItem = questionItem;
        BloQueryApplication.getSharedDataSource().fetchAnswers(questionItem, new DataSource.Callback() {
            @Override
            public void onSuccess() {
                AnswersFragment af = AnswersFragment.newInstance(questionItem.getQuestionOwner().getUsername() + " asks : " + questionItem.getQuestion());

                getFragmentManager()
                        .beginTransaction()
                        .hide(currentFragment)
                        .addToBackStack(null)
                        .add(R.id.fl_activity_main, af, "Answer")
                        .commit();

                answerIcons();
                currentFragment = af;
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    @Override
    public void onUserClicked(QuestionItemAdapter itemAdapter, Question questionItem) {

        BloQueryApplication.getSharedDataSource().setCurrentViewedUser((BloQueryUser) questionItem.getQuestionOwner());

        ProfileViewFragment pvf = new ProfileViewFragment();
        getFragmentManager()
                .beginTransaction()
                .hide(currentFragment)
                .addToBackStack(null)
                .add(R.id.fl_activity_main, pvf, "ProfileView")
                .commit();
        profileIcons();
    }


    @Override
    public void onBackPressed() {
        if((getFragmentManager().findFragmentByTag("Profile") != null && getFragmentManager().findFragmentByTag("Profile").isVisible()) ||
                getFragmentManager().findFragmentByTag("ProfileView") != null && getFragmentManager().findFragmentByTag("ProfileView").isVisible()){
            getFragmentManager().popBackStackImmediate();
            if(getFragmentManager().findFragmentByTag("Question").isVisible()){
                currentFragment = getFragmentManager().findFragmentByTag("Question");
                questionIcons();
            }else{
                currentFragment = getFragmentManager().findFragmentByTag("Answer");
                answerIcons();
            }
        }else if(getFragmentManager().findFragmentByTag("Answer") != null && getFragmentManager().findFragmentByTag("Answer").isVisible()){
            getFragmentManager().popBackStackImmediate();
            currentFragment = getFragmentManager().findFragmentByTag("Question");
            questionIcons();
        }else if(getFragmentManager().findFragmentByTag("Question").isVisible()){
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_question:
                QuestionsDialogFragment qdf = new QuestionsDialogFragment();
                qdf.show(getFragmentManager(), "Question");
                break;
            case R.id.action_answer:
                AnswersDialogFragment adf = new AnswersDialogFragment();
                adf.show(getFragmentManager(), "Answer");
                break;
            case R.id.action_refresh:
                if(currentFragment instanceof QuestionsFragment){
                    BloQueryApplication.getSharedDataSource().refreshQuestions(new DataSource.Callback() {
                        @Override
                        public void onSuccess() {
                            ((QuestionsFragment) currentFragment).notifyAdapter();
                        }

                        @Override
                        public void onError(String errorMessage) {

                        }
                    });

                }else{
                    BloQueryApplication.getSharedDataSource().fetchAnswers(clickedItem, new DataSource.Callback() {
                        @Override
                        public void onSuccess() {
                            ((AnswersFragment)currentFragment).notifyAdapter();
                        }

                        @Override
                        public void onError(String errorMessage) {

                        }
                    });
                }
                break;
            case R.id.action_profile:
                profileIcons();

                ProfileFragment pf = new ProfileFragment();

                getFragmentManager()
                        .beginTransaction()
                        .hide(currentFragment)
                        .addToBackStack(null)
                        .add(R.id.fl_activity_main, pf, "Profile")
                        .commit();
                currentFragment = pf;
                break;
            case R.id.action_sortby_date:
                ParseQuery<Answer> queryDate = ParseQuery.getQuery(Answer.class);
                queryDate.whereEqualTo("question", clickedItem);
                queryDate.orderByDescending("createdAt");
                queryDate.findInBackground(new FindCallback<Answer>() {
                    @Override
                    public void done(List<Answer> answers, ParseException e) {
                        BloQueryApplication.getSharedDataSource().setAnswers(answers);

                        Fragment f = getFragmentManager().findFragmentByTag("Answer");
                        AnswersFragment af = (AnswersFragment) f;
                        af.notifyAdapter();
                    }
                });
                break;
            case R.id.action_sortby_upvote:
                ParseQuery<Answer> queryVote = ParseQuery.getQuery(Answer.class);
                queryVote.whereEqualTo("question", clickedItem);
                queryVote.orderByDescending("votes");
                queryVote.findInBackground(new FindCallback<Answer>() {
                    @Override
                    public void done(List<Answer> answers, ParseException e) {
                        BloQueryApplication.getSharedDataSource().setAnswers(answers);

                        Fragment f = getFragmentManager().findFragmentByTag("Answer");
                        AnswersFragment af = (AnswersFragment) f;
                        af.notifyAdapter();
                    }
                });
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void questionIcons(){
        Menu menu = toolbar.getMenu();
        menu.findItem(R.id.action_question).setVisible(true);
        menu.findItem(R.id.action_answer).setVisible(false);
        menu.findItem(R.id.action_profile).setVisible(true);
        menu.findItem(R.id.action_refresh).setVisible(true);
        menu.findItem(R.id.action_sortby_date).setVisible(false);
        menu.findItem(R.id.action_sortby_upvote).setVisible(false);
    }

    public void answerIcons(){
        Menu menu = toolbar.getMenu();
        menu.findItem(R.id.action_question).setVisible(false);
        menu.findItem(R.id.action_answer).setVisible(true);
        menu.findItem(R.id.action_refresh).setVisible(true);
        menu.findItem(R.id.action_profile).setVisible(true);
        menu.findItem(R.id.action_sortby_date).setVisible(true);
        menu.findItem(R.id.action_sortby_upvote).setVisible(true);
    }

    public void profileIcons(){
        Menu menu = toolbar.getMenu();
        menu.findItem(R.id.action_question).setVisible(false);
        menu.findItem(R.id.action_answer).setVisible(false);
        menu.findItem(R.id.action_refresh).setVisible(false);
        menu.findItem(R.id.action_profile).setVisible(false);
        menu.findItem(R.id.action_sortby_date).setVisible(false);
        menu.findItem(R.id.action_sortby_upvote).setVisible(false);
    }

    @Override
    public void onSubmitQuestionDialog(String inputText) {
        Question q = new Question(inputText, ParseUser.getCurrentUser());
        q.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                ParseQuery<Question> query = ParseQuery.getQuery("Question");
                query.orderByDescending("createdAt");
                query.setLimit(10);
                query.findInBackground(new FindCallback<Question>() {
                    public void done(List<Question> questionsList, ParseException e) {
                        BloQueryApplication.getSharedDataSource().setQuestions(questionsList);
                        Fragment f = getFragmentManager().findFragmentByTag("Question");
                        QuestionsFragment qf = (QuestionsFragment) f;
                        qf.notifyAdapter();
                    }
                });
            }
        });
    }

    @Override
    public void onSubmitAnswerDialog(String inputText) {
        clickedItem.increment("answers");
        clickedItem.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                ((QuestionsFragment) getFragmentManager().findFragmentByTag("Question")).notifyAdapter();
            }
        });

        Answer a = new Answer(inputText, clickedItem, BloQueryApplication.getSharedDataSource().getCurrentUser());
        a.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                BloQueryApplication.getSharedDataSource().fetchAnswers(clickedItem, new DataSource.Callback() {
                    @Override
                    public void onSuccess() {
                        ((AnswersFragment) getFragmentManager().findFragmentByTag("Answer")).notifyAdapter();
                    }

                    @Override
                    public void onError(String errorMessage) {

                    }
                });
            }
        });
    }
}

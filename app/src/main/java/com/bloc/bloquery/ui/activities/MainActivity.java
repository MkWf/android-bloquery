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
import com.bloc.bloquery.ui.fragments.QuestionsDialogFragment;
import com.bloc.bloquery.ui.fragments.QuestionsFragment;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
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

    Fragment listFragment;
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

        BloQueryApplication.getSharedDataSource().setCurrentUser((BloQueryUser)BloQueryUser.getCurrentUser());

        listFragment = new QuestionsFragment();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fl_activity_main, listFragment, "Question")
                .commit();
    }

    @Override
    public void onItemClicked(QuestionItemAdapter itemAdapter, Question questionItem) {
        clickedItem = questionItem;
        BloQueryApplication.getSharedDataSource().fetchAnswers(questionItem.getObjectId(), new DataSource.Callback() {
            @Override
            public void onSuccess() {
                getFragmentManager()
                        .beginTransaction()
                        .hide(listFragment)
                        .addToBackStack(null)
                        .add(R.id.fl_activity_main, new AnswersFragment(), "Answer")
                        .commit();
                answerIcons();
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        getFragmentManager().popBackStack();
        questionIcons();
        if(getFragmentManager().getBackStackEntryCount() <= 0){
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
            case R.id.action_profile:
                profileIcons();
                getFragmentManager()
                        .beginTransaction()
                        .hide(listFragment)
                        .addToBackStack(null)
                        .add(R.id.fl_activity_main, new ProfileFragment(), "Profile")
                        .commit();
        }

        return super.onOptionsItemSelected(item);
    }

    public void questionIcons(){
        Menu menu = toolbar.getMenu();
        menu.findItem(R.id.action_question).setVisible(true);
        menu.findItem(R.id.action_answer).setVisible(false);
        menu.findItem(R.id.action_profile).setVisible(true);
    }

    public void answerIcons(){
        Menu menu = toolbar.getMenu();
        menu.findItem(R.id.action_question).setVisible(false);
        menu.findItem(R.id.action_answer).setVisible(true);
        menu.findItem(R.id.action_profile).setVisible(true);
    }

    public void profileIcons(){
        Menu menu = toolbar.getMenu();
        menu.findItem(R.id.action_question).setVisible(false);
        menu.findItem(R.id.action_answer).setVisible(false);
        menu.findItem(R.id.action_profile).setVisible(false);
    }

    @Override
    public void onSubmitQuestionDialog(String inputText) {
        Question q = new Question(inputText);
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
                Fragment f = getFragmentManager().findFragmentByTag("Question");
                QuestionsFragment qf = (QuestionsFragment) f;
                qf.notifyAdapter();
            }
        });

        Answer a = new Answer(inputText, clickedItem.getObjectId());
        a.put("parent", clickedItem.getObjectId());
        a.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                BloQueryApplication.getSharedDataSource().fetchAnswers(clickedItem.getObjectId(), new DataSource.Callback() {
                    @Override
                    public void onSuccess() {
                        Fragment f = getFragmentManager().findFragmentByTag("Answer");
                        AnswersFragment af = (AnswersFragment) f;
                        af.notifyAdapter();
                    }

                    @Override
                    public void onError(String errorMessage) {

                    }
                });
            }
        });
    }
}

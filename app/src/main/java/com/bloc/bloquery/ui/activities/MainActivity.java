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
import com.bloc.bloquery.ui.fragments.QuestionsDialogFragment;
import com.bloc.bloquery.ui.fragments.QuestionsFragment;
import com.parse.ParseException;
import com.parse.SaveCallback;
import com.parse.models.Answer;
import com.parse.models.Question;
import com.parse.ui.ParseLoginBuilder;

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
        setSupportActionBar(toolbar);

        ParseLoginBuilder builder = new ParseLoginBuilder(MainActivity.this);
        startActivityForResult(builder.build(), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        listFragment = new QuestionsFragment();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fl_activity_main, listFragment)
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
                break;
            case R.id.action_answer:
                AnswersDialogFragment adf = new AnswersDialogFragment();
                adf.show(getFragmentManager(), "Answer");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void questionIcons(){
        Menu menu = toolbar.getMenu();
        menu.findItem(R.id.action_question).setVisible(true);
        menu.findItem(R.id.action_answer).setVisible(false);
    }

    public void answerIcons(){
        Menu menu = toolbar.getMenu();
        menu.findItem(R.id.action_question).setVisible(false);
        menu.findItem(R.id.action_answer).setVisible(true);
    }

    @Override
    public void onSubmitQuestionDialog(String inputText) {
        Question q = new Question(inputText);
    }

    @Override
    public void onSubmitAnswerDialog(String inputText) {
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

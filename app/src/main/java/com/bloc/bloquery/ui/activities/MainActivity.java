package com.bloc.bloquery.ui.activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.bloc.bloquery.R;
import com.bloc.bloquery.adapters.QuestionItemAdapter;
import com.bloc.bloquery.ui.fragments.AnswersFragment;
import com.bloc.bloquery.ui.fragments.QuestionsFragment;
import com.parse.models.Question;
import com.parse.ui.ParseLoginBuilder;

/**
 * Created by Mark on 2/25/2015.
 */
public class MainActivity extends ActionBarActivity implements QuestionsFragment.Delegate {

    Fragment listFragment;
    Toolbar toolbar;

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

        getFragmentManager()
                .beginTransaction()
                .hide(listFragment)
                .addToBackStack(null)
                .add(R.id.fl_activity_main, new AnswersFragment())
                .commit();
        answerIcons();
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
}

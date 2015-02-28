package com.bloc.bloquery.ui.activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

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

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }

    @Override
    public void onBackPressed() {
        getFragmentManager().popBackStack();
        if(getFragmentManager().getBackStackEntryCount() <= 0){
            super.onBackPressed();
        }
    }
}

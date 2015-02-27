package com.bloc.bloquery.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.bloc.bloquery.R;
import com.bloc.bloquery.ui.fragments.QuestionsFragment;
import com.parse.ui.ParseLoginBuilder;

/**
 * Created by Mark on 2/25/2015.
 */
public class MainActivity extends ActionBarActivity {

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
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fl_activity_main, new QuestionsFragment())
                .commit();
    }
}

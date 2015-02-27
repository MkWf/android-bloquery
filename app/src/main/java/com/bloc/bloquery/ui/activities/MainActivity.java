package com.bloc.bloquery.ui.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.parse.ui.ParseLoginBuilder;

/**
 * Created by Mark on 2/25/2015.
 */
public class MainActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        ParseLoginBuilder builder = new ParseLoginBuilder(MainActivity.this);
        startActivityForResult(builder.build(), 0);
    }

}

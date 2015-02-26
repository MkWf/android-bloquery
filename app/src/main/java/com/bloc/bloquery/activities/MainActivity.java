package com.bloc.bloquery.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;

import com.bloc.bloquery.R;

/**
 * Created by Mark on 2/25/2015.
 */
public class MainActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText username = (EditText) findViewById(R.id.main_username);
        EditText password = (EditText) findViewById(R.id.main_username);

    }

}

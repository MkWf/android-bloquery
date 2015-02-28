package com.bloc.bloquery;

import android.app.Application;

import com.bloc.bloquery.api.DataSource;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.models.Answer;
import com.parse.models.BloQueryUser;
import com.parse.models.Question;

/**
 * Created by Mark on 2/26/2015.
 */
public class BloQueryApplication extends Application {
    public static BloQueryApplication getSharedInstance() {
        return sharedInstance;
    }

    public static DataSource getSharedDataSource() {
        return BloQueryApplication.getSharedInstance().getDataSource();
    }

    private static BloQueryApplication sharedInstance;
    private DataSource dataSource;


    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        sharedInstance = this;

        ParseObject.registerSubclass(BloQueryUser.class);
        ParseObject.registerSubclass(Question.class);
        ParseObject.registerSubclass(Answer.class);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, getResources().getString(R.string.parse_app_id), getResources().getString(R.string.parse_client_key));

        dataSource = new DataSource();
    }
}


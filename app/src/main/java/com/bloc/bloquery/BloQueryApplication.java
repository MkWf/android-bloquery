package com.bloc.bloquery;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.models.BloQueryUser;
import com.parse.models.Question;

/**
 * Created by Mark on 2/26/2015.
 */
public class BloQueryApplication extends Application {
        @Override
        public void onCreate(){
            super.onCreate();
            ParseObject.registerSubclass(BloQueryUser.class);
            ParseObject.registerSubclass(Question.class);
            Parse.enableLocalDatastore(this);
            Parse.initialize(this, getResources().getString(R.string.parse_app_id), getResources().getString(R.string.parse_client_key));
        }
}


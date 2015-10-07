package com.parse.models;

import com.parse.ParseFile;
import com.parse.ParseUser;

/**
 * Created by Mark on 2/26/2015.
 */
public class BloQueryUser extends ParseUser {

    public static String DESCRIPTION = "description";
    public static String IMAGE = "image";
    public static String NAME = "name";

    public BloQueryUser(){

    }

    public BloQueryUser(String description){
        setProfileDescription(DESCRIPTION);
    }

    public String getProfileDescription(){
        return getString(DESCRIPTION);
    }

    public void setProfileDescription(String profileDescription) {
        put(DESCRIPTION, profileDescription);
    }

    public void setProfileImage(ParseFile profileImage){
        put(IMAGE, profileImage);
    }

    public String getUserName(){
        return getString(NAME);
    }
}

package com.parse.models;

import com.parse.ParseUser;

/**
 * Created by Mark on 2/26/2015.
 */
public class BloQueryUser extends ParseUser {

    public BloQueryUser(){

    }

    public BloQueryUser(String description){
        setProfileDescription("description");
    }

    public String getProfileDescription(){
        return getString("description");
    }

    //public String getProfileImage() {return profileImage;}

    public void setProfileDescription(String profileDescription) {
        put("description", profileDescription);
    }

    public String getUserName(){
        return getString("name");
    }

   // public void setProfileImage(String profileImage) {
      //  this.profileImage = profileImage;
    //}
}

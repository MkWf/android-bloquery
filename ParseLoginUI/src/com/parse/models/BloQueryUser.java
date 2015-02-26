package com.parse.models;

import com.parse.ParseUser;

/**
 * Created by Mark on 2/26/2015.
 */
public class BloQueryUser extends ParseUser {

    String profileDescription;
    String profileImage;

    public String getProfileDescription(){
        return profileDescription;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}

package com.parse.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.json.JSONArray;

/**
 * Created by Mark on 2/28/2015.
 */

@ParseClassName("Answer")
public class Answer extends ParseObject {

    public Answer(){}

    public Answer(String answer, String parent, String user){
        setAnswer(answer);
        setParent(parent);
        setParentUser(user);
        setVotes(0);
        put("usersVoted", new JSONArray());
    }

    public String getAnswer() {
        return getString("answer");
    }

    public String getParent(){
        return getString("parent");
    }

    public String getParentUser() {
        return getString("parentUser");
    }

    public int getVotes() { return getInt("votes");}

    public JSONArray getUsersWhoVoted() { return getJSONArray("usersVoted");}

    public void setAnswer(String answer) {
        put("answer", answer);
    }

    public void setParent(String question){
        put("parent", question);
    }

    public void setParentUser(String user) { put("parentUser", user);}

    public void setVotes(int votes) {put("votes", votes);}

    public void setUserWhoVoted(BloQueryUser user) { put("usersVoted", user);}

}

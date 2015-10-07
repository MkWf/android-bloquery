package com.parse.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;

/**
 * Created by Mark on 2/28/2015.
 */

@ParseClassName("Answer")
public class Answer extends ParseObject {

    private boolean isUpVote;

    public Answer(){}

    public Answer(String answer, Question question, ParseUser answerOwner){
        setAnswer(answer);
        setQuestion(question);
        setAnswerOwner(answerOwner);
        setVotes(0);
        put("usersVoted", new JSONArray());
        setUpVote(false);
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

    public void setAnswerOwner(ParseUser user){
        put("answerOwner", user);
    }

    public ParseUser getAnswerOwner() { return getParseUser("answerOwner"); }

    public void setQuestion(Question question) { put("question", question);}

    public void setVotes(int votes) {put("votes", votes);}

    public void setUserWhoVoted(BloQueryUser user) { put("usersVoted", user);}

    public boolean isUpVote(){ return isUpVote;}

    public void setUpVote(boolean isUpVote) {this.isUpVote = isUpVote; }

}

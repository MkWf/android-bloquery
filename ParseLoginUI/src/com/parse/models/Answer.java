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

    public static String QUESTION = "question";
    public static String USERS_VOTED = "usersVoted";
    public static String VOTES = "votes";
    public static String ANSWER = "answer";
    public static String ANSWER_OWNER = "answerOwner";
    public static String PARENT = "parent";

    public static String NAME = "Answer";

    private boolean isUpVote;

    public Answer(){}

    public Answer(String answer, Question question, ParseUser answerOwner){
        setAnswer(answer);
        setQuestion(question);
        setAnswerOwner(answerOwner);
        setVotes(0);
        initUsersVoted();
        setUpVote(false);
    }

    public String getAnswer() {
        return getString(ANSWER);
    }

    public String getParent(){
        return getString(PARENT);
    }

    public int getVotes() { return getInt(VOTES);}

    public JSONArray getUsersWhoVoted() { return getJSONArray(USERS_VOTED);}

    public void setAnswer(String answer) { put(ANSWER, answer); }

    public void setAnswerOwner(ParseUser user){
        put(ANSWER_OWNER, user);
    }

    public ParseUser getAnswerOwner() { return getParseUser(ANSWER_OWNER); }

    public void setQuestion(Question question) { put(QUESTION, question);}

    public void initUsersVoted() { put(USERS_VOTED, new JSONArray());}

    public void setVotes(int votes) {put(VOTES, votes);}

    public void addUserWhoVoted(BloQueryUser user) { put(USERS_VOTED, user);}

    public boolean isUpVote(){ return isUpVote;}

    public void setUpVote(boolean isUpVote) {this.isUpVote = isUpVote; }

}

package com.bloc.bloquery.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bloc.bloquery.BloQueryApplication;
import com.bloc.bloquery.R;
import com.bloc.bloquery.adapters.AnswersItemAdapter;
import com.parse.ParseException;
import com.parse.SaveCallback;
import com.parse.models.Answer;

/**
 * Created by Mark on 2/27/2015.
 */
public class AnswersFragment extends Fragment implements AnswersItemAdapter.Delegate {
    AnswersItemAdapter itemAdapter;
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemAdapter = new AnswersItemAdapter();
        itemAdapter.setDelegate(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_answers_list, container, false);
        recyclerView = (RecyclerView) inflate.findViewById(R.id.rv_fragment_answers);
        return inflate;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemAdapter);
    }

    public void notifyAdapter(){
        itemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onUpvoteClicked(AnswersItemAdapter itemAdapter, final Answer answerItem) {
        /*HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("objectId", answerItem.getObjectId());
        params.put("userId", BloQueryApplication.getSharedDataSource().getCurrentUser().getObjectId());
        ParseCloud.callFunctionInBackground("hasUserVotedAlready", params, new FunctionCallback<Boolean>() {
            @Override
            public void done(Boolean hasVoted, com.parse.ParseException e) {
                if(hasVoted){
                    answerItem.setVotes(answerItem.getVotes() - 1);
                    JSONArray array = answerItem.getJSONArray("usersVoted");
                }
            }
        });*/


        answerItem.increment("votes");
        answerItem.addUnique("usersVoted", BloQueryApplication.getSharedDataSource().getCurrentUser());
        answerItem.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                //Fragment f = getFragmentManager().findFragmentByTag("Answer");
                //QuestionsFragment qf = (QuestionsFragment) f;
                //qf.notifyAdapter();
                notifyAdapter();
            }
        });
    }
}

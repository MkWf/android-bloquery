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
import android.widget.TextView;

import com.bloc.bloquery.BloQueryApplication;
import com.bloc.bloquery.R;
import com.bloc.bloquery.adapters.AnswersItemAdapter;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.models.Answer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mark on 2/27/2015.
 */
public class AnswersFragment extends Fragment implements AnswersItemAdapter.Delegate {
    private static final String TITLE = "title";
    AnswersItemAdapter itemAdapter;
    RecyclerView recyclerView;
    TextView question;

    public static AnswersFragment newInstance(String title) {
        Bundle args = new Bundle();

        AnswersFragment fragment = new AnswersFragment();
        args.putString(TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

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
        question = (TextView) inflate.findViewById(R.id.question_title);
        question.setText(getArguments().getString(TITLE, "Title"));
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
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                itemAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onUpvoteClicked(AnswersItemAdapter itemAdapter, final Answer answerItem) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("objectId", answerItem.getObjectId());
        params.put("userId", BloQueryApplication.getSharedDataSource().getCurrentUser().getObjectId());
        ParseCloud.callFunctionInBackground("setUserVote", params, new FunctionCallback<Map<String, Object>>() {
            @Override
            public void done(Map<String, Object> mapObject, com.parse.ParseException e) {
                if (e == null) {
                    int votes = (Integer) mapObject.get("result");
                    if (answerItem.getVotes() > votes) {
                        answerItem.setUpVote(false);
                    } else {
                        answerItem.setUpVote(true);
                    }
                    answerItem.setVotes(votes);
                    notifyAdapter();
                }
            }
        });
    }
}

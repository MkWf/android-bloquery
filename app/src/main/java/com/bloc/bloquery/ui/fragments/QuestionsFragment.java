package com.bloc.bloquery.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bloc.bloquery.R;
import com.bloc.bloquery.adapters.QuestionItemAdapter;
import com.parse.models.Question;

/**
 * Created by Mark on 2/27/2015.
 */
public class QuestionsFragment extends Fragment implements QuestionItemAdapter.Delegate{

    public static interface Delegate {
        public void onItemClicked(QuestionItemAdapter itemAdapter, Question questionItem);
        public void onUserClicked(QuestionItemAdapter itemAdapter, Question questionItem);
    }

    QuestionItemAdapter questionItemAdapter;
    RecyclerView recyclerView;
    Delegate listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            listener = (Delegate) activity;
        }catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnItemClicked");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionItemAdapter = new QuestionItemAdapter();
        questionItemAdapter.setDelegate(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_questions_list, container, false);
        recyclerView = (RecyclerView) inflate.findViewById(R.id.rv_fragment_questions);
        return inflate;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(questionItemAdapter);
    }

    @Override
    public void onItemClicked(QuestionItemAdapter itemAdapter, Question questionItem) {
        listener.onItemClicked(itemAdapter, questionItem);
    }

    @Override
    public void onUserClicked(QuestionItemAdapter itemAdapter, Question questionItem) {
        listener.onUserClicked(itemAdapter, questionItem);
    }

    public void notifyAdapter(){
        questionItemAdapter.notifyDataSetChanged();
    }
}

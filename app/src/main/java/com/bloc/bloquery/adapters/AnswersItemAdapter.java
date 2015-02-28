package com.bloc.bloquery.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bloc.bloquery.BloQueryApplication;
import com.bloc.bloquery.R;
import com.bloc.bloquery.api.DataSource;
import com.parse.models.Question;

/**
 * Created by Mark on 2/27/2015.
 */
public class AnswersItemAdapter extends RecyclerView.Adapter<AnswersItemAdapter.ItemAdapterViewHolder> {

    @Override
    public ItemAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int index) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.answer_item, viewGroup, false);
        return new ItemAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ItemAdapterViewHolder itemAdapterViewHolder, int index) {
        DataSource sharedDataSource = BloQueryApplication.getSharedDataSource();
        itemAdapterViewHolder.update(sharedDataSource.getAnswers().get(index));
    }

    @Override
    public int getItemCount() {
        return BloQueryApplication.getSharedDataSource().getAnswers().size();
    }

    class ItemAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView answer;
        TextView votes;
        ImageButton upvote;
        ImageButton moreOptions;

        public ItemAdapterViewHolder(View itemView) {
            super(itemView);

            answer = (TextView) itemView.findViewById(R.id.answer_item_answer);
            votes = (TextView) itemView.findViewById(R.id.answer_item_votes);
            upvote = (ImageButton) itemView.findViewById(R.id.answer_item_upvote);
            moreOptions = (ImageButton) itemView.findViewById(R.id.answer_item_more_options);
        }

        void update(Question questionItem) {
            answer.setText(questionItem.getQuestion());
            votes.setText("100 votes");
        }
    }
}


package com.bloc.bloquery.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bloc.bloquery.BloQueryApplication;
import com.bloc.bloquery.R;
import com.bloc.bloquery.api.DataSource;
import com.parse.models.Question;

import java.lang.ref.WeakReference;

/**
 * Created by Mark on 2/26/2015.
 */
public class QuestionItemAdapter extends RecyclerView.Adapter<QuestionItemAdapter.ItemAdapterViewHolder> {

    public static interface Delegate {
        public void onItemClicked(QuestionItemAdapter itemAdapter, Question questionItem);
    }

    private WeakReference<Delegate> delegate;

    public Delegate getDelegate() {
        if (delegate == null) {
            return null;
        }
        return delegate.get();
    }
    public void setDelegate(Delegate delegate) {
        this.delegate = new WeakReference<Delegate>(delegate);
    }

    @Override
    public ItemAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int index) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.question_item, viewGroup, false);
        return new ItemAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ItemAdapterViewHolder itemAdapterViewHolder, int index) {
        DataSource sharedDataSource = BloQueryApplication.getSharedDataSource();
        itemAdapterViewHolder.update(sharedDataSource.getQuestions().get(index));
    }

    @Override
    public int getItemCount() {
        return BloQueryApplication.getSharedDataSource().getQuestions().size();
    }

    class ItemAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView question;
        Button answers;
        ImageView rating;
        ImageButton user;
        Question questionItem;

        public ItemAdapterViewHolder(View itemView) {
            super(itemView);

            question = (TextView) itemView.findViewById(R.id.question_item_question);
            answers = (Button) itemView.findViewById(R.id.queston_item_answers_button);

            itemView.setOnClickListener(this);
        }

        void update(Question questionItem) {
            this.questionItem = questionItem;
            question.setText(questionItem.getQuestion());
            answers.setText("10 answers");
        }

        @Override
        public void onClick(View view) {
            getDelegate().onItemClicked(QuestionItemAdapter.this, questionItem);
        }
    }
}

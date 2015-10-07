package com.bloc.bloquery.adapters;

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bloc.bloquery.BloQueryApplication;
import com.bloc.bloquery.R;
import com.bloc.bloquery.api.DataSource;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.models.BloQueryUser;
import com.parse.models.Question;

import java.lang.ref.WeakReference;

/**
 * Created by Mark on 2/26/2015.
 */
public class QuestionItemAdapter extends RecyclerView.Adapter<QuestionItemAdapter.ItemAdapterViewHolder> {

    public static interface Delegate {
        public void onItemClicked(QuestionItemAdapter itemAdapter, Question questionItem);
        public void onUserClicked(QuestionItemAdapter itemAdapter, Question questionItem);
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
        TextView answers;
        ImageButton userImage;
        TextView userName;
        Question questionItem;

        public ItemAdapterViewHolder(View itemView) {
            super(itemView);

            question = (TextView) itemView.findViewById(R.id.question_item_question);
            answers = (TextView) itemView.findViewById(R.id.queston_item_answers);
            userImage = (ImageButton) itemView.findViewById(R.id.question_item_user_image);
            userName = (TextView) itemView.findViewById(R.id.question_item_user_name);

            itemView.setOnClickListener(this);
            userImage.setOnClickListener(this);
        }

        void update(Question questionItem) {
            this.questionItem = questionItem;
            question.setText(questionItem.getQuestion());
            answers.setText(Integer.toString(questionItem.getAnswers()) + " answers");

            ParseUser questionOwner = questionItem.getQuestionOwner();

            String name = "";
            try{
                name = questionOwner.fetchIfNeeded().getUsername();
            }catch(ParseException e){
                e.printStackTrace();
            }
            userName.setText(name);
            ParseFile file = (ParseFile) questionOwner.get(BloQueryUser.IMAGE);
            file.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, ParseException e) {
                    if (e == null) {
                        int targetW = userImage.getWidth();
                        int targetH = userImage.getHeight();

                        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                        bmOptions.inJustDecodeBounds = true;
                        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, bmOptions);

                        int photoW = bmOptions.outWidth;
                        int photoH = bmOptions.outHeight;

                        int scaleFactor = Math.max(photoW / targetW, photoH / targetH);

                        bmOptions.inJustDecodeBounds = false;
                        bmOptions.inSampleSize = scaleFactor;

                        userImage.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length, bmOptions));
                    }
                }
            });
        }

        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.question_item_user_image:
                    getDelegate().onUserClicked(QuestionItemAdapter.this, questionItem);
                    break;
                default:
                    getDelegate().onItemClicked(QuestionItemAdapter.this, questionItem);
                    break;
            }

        }
    }

}

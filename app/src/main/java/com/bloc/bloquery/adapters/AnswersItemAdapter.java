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
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.models.Answer;
import com.parse.models.BloQueryUser;

import java.util.List;

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
        ImageButton user;

        public ItemAdapterViewHolder(View itemView) {
            super(itemView);

            answer = (TextView) itemView.findViewById(R.id.answer_item_answer);
            votes = (TextView) itemView.findViewById(R.id.answer_item_votes);
            upvote = (ImageButton) itemView.findViewById(R.id.answer_item_upvote);
            moreOptions = (ImageButton) itemView.findViewById(R.id.answer_item_more_options);
            user = (ImageButton) itemView.findViewById(R.id.answer_item_user);
        }

        void update(Answer answerItem) {
            answer.setText(answerItem.getAnswer());
            votes.setText("100 votes");

            ParseQuery<ParseUser> query = BloQueryUser.getQuery();
            query.whereEqualTo("objectId", answerItem.getParentUser());
            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> parseUsers, ParseException e) {
                    ParseFile file = (ParseFile) parseUsers.get(0).get("image");
                    file.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] bytes, ParseException e) {
                            if (e == null) {
                                int targetW = user.getWidth();
                                int targetH = user.getHeight();

                                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                                bmOptions.inJustDecodeBounds = true;
                                BitmapFactory.decodeByteArray(bytes, 0, bytes.length, bmOptions);

                                int photoW = bmOptions.outWidth;
                                int photoH = bmOptions.outHeight;

                                int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

                                bmOptions.inJustDecodeBounds = false;
                                bmOptions.inSampleSize = scaleFactor;
                                //bmOptions.inPurgeable = true;

                                user.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length, bmOptions));
                            }
                        }
                    });
                }
            });
        }
    }
}


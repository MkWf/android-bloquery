package com.bloc.bloquery.ui.fragments;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bloc.bloquery.BloQueryApplication;
import com.bloc.bloquery.R;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.models.BloQueryUser;

/**
 * Created by Mark on 3/5/2015.
 */
public class ProfileViewFragment extends Fragment {
    private static final int PICK_IMAGE = 1;

    Button descriptionButton;
    TextView user;
    ImageView userImage;
    TextView description;
    EditText editDescription;
    Button uploadImageButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_profile, container, false);

        user = (TextView) inflate.findViewById(R.id.fragment_profile_username);
        userImage = (ImageView) inflate.findViewById(R.id.fragment_profile_image);
        description = (TextView) inflate.findViewById(R.id.fragment_profile_description);
        editDescription = (EditText) inflate.findViewById(R.id.fragment_profile_edit_description);
        uploadImageButton = (Button) inflate.findViewById(R.id.fragment_profile_upload_image);
        descriptionButton = (Button) inflate.findViewById(R.id.fragment_profile_description_button);

        uploadImageButton.setVisibility(View.GONE);
        descriptionButton.setVisibility(View.GONE);

        user.setText(BloQueryApplication.getSharedDataSource().getCurrentViewedUser().getUserName() + "'s Profile");
        description.setText(BloQueryApplication.getSharedDataSource().getCurrentViewedUser().getProfileDescription());

        ParseFile image = (ParseFile) BloQueryApplication.getSharedDataSource().getCurrentViewedUser().get(BloQueryUser.IMAGE);
        image.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, ParseException e) {
                if(e == null){
                    userImage.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                    Fragment f = getFragmentManager().findFragmentByTag("Question");
                    QuestionsFragment qf = (QuestionsFragment) f;
                    qf.notifyAdapter();

                    f = getFragmentManager().findFragmentByTag("Answer");
                    if(f != null){
                        AnswersFragment af = (AnswersFragment) f;
                        af.notifyAdapter();
                    }
                }
            }
        });

        return inflate;
    }
}

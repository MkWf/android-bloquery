package com.bloc.bloquery.ui.fragments;

import android.app.Fragment;
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

/**
 * Created by Mark on 3/2/2015.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    Button descriptionButton;
    TextView user;
    ImageView userImage;
    TextView description;
    EditText editDescription;
    Button uploadImage;

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
        uploadImage = (Button) inflate.findViewById(R.id.fragment_profile_upload_image);
        descriptionButton = (Button) inflate.findViewById(R.id.fragment_profile_description_button);

        description.setText(BloQueryApplication.getSharedDataSource().getCurrentUser().getProfileDescription());
        editDescription.setText(description.getText());

        descriptionButton.setOnClickListener(this);

        return inflate;
    }

    @Override
    public void onClick(View v) {
        if(description.getVisibility() == View.VISIBLE){
            description.setVisibility(View.GONE);
            editDescription.setVisibility(View.VISIBLE);
            editDescription.requestFocus();
            descriptionButton.setText("Save Description");
        }else{
            BloQueryApplication.getSharedDataSource().getCurrentUser().setProfileDescription(editDescription.getText().toString());
            BloQueryApplication.getSharedDataSource().getCurrentUser().saveInBackground();
            description.setText(editDescription.getText());
            description.setVisibility(View.VISIBLE);
            editDescription.setVisibility(View.GONE);
            descriptionButton.setText("Edit Description");
        }
    }
}


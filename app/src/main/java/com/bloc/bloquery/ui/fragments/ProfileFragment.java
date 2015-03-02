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

import com.bloc.bloquery.R;

/**
 * Created by Mark on 3/2/2015.
 */
public class ProfileFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView user = (TextView) inflate.findViewById(R.id.fragment_profile_username);
        ImageView userImage = (ImageView) inflate.findViewById(R.id.fragment_profile_image);
        TextView description = (TextView) inflate.findViewById(R.id.fragment_profile_description);
        EditText editDescription = (EditText) inflate.findViewById(R.id.fragment_profile_edit_description);
        Button uploadImage = (Button) inflate.findViewById(R.id.fragment_profile_upload_image);
        Button descriptionButton = (Button) inflate.findViewById(R.id.fragment_profile_description_button);

        return inflate;
    }
}

package com.bloc.bloquery.ui.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
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

        user.setText(BloQueryApplication.getSharedDataSource().getCurrentUser().getUserName() + "'s Profile");

        description.setText(BloQueryApplication.getSharedDataSource().getCurrentUser().getProfileDescription());
        editDescription.setText(description.getText());

        uploadImageButton.setOnClickListener(this);
        descriptionButton.setOnClickListener(this);

        return inflate;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.fragment_profile_description_button:
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
                break;
            case R.id.fragment_profile_upload_image:
                /*Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);*/
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, PICK_IMAGE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri selectedImage = data.getData();
        userImage.setImageURI(selectedImage);
        /*Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };

        Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

        Bitmap bmp = BitmapFactory.decodeFile(picturePath);
        userImage.setImageBitmap(bmp);*/
    }
}


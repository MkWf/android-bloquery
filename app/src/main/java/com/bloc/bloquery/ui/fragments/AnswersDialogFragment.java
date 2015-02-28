package com.bloc.bloquery.ui.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bloc.bloquery.R;

/**
 * Created by Mark on 2/28/2015.
 */
public class AnswersDialogFragment extends DialogFragment implements View.OnClickListener {

    public interface SubmitDialogListener {
        void onSubmitDialog(String inputText);
    }

    EditText answer;
    ImageButton submit;

    public AnswersDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_answers_dialog, container);
        answer = (EditText) view.findViewById(R.id.fragment_answers_dialog_answer);
        submit = (ImageButton) view.findViewById(R.id.fragment_answers_dialog_submit);

        answer.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        submit.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        SubmitDialogListener activity = (SubmitDialogListener) getActivity();
        activity.onSubmitDialog(answer.getText().toString());
        this.dismiss();
    }
}


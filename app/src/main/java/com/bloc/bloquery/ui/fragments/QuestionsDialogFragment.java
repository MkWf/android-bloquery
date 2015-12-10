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
 * Created by Mark on 3/1/2015.
 */
public class QuestionsDialogFragment extends DialogFragment implements View.OnClickListener {
    public interface SubmitQuestionDialogListener {
        void onSubmitQuestionDialog(String inputText);
    }

    EditText question;
    ImageButton submit;

    public QuestionsDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_questions_dialog, container);
        question = (EditText) view.findViewById(R.id.fragment_questions_dialog_question);
        submit = (ImageButton) view.findViewById(R.id.fragment_questions_dialog_submit);

        question.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().setTitle("Type your question");
        submit.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        SubmitQuestionDialogListener activity = (SubmitQuestionDialogListener) getActivity();
        activity.onSubmitQuestionDialog(question.getText().toString());
        this.dismiss();
    }
}

package com.example.soul_searching.ui.set_question;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.soul_searching.R;

public class SetQuestion extends Fragment {

    private SetQuestionlViewModel setquestionViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setquestionViewModel =
                new ViewModelProvider(this).get(SetQuestionlViewModel.class);
        View root = inflater.inflate(R.layout.set_question, container, false);
        final TextView textView = root.findViewById(R.id.set_question);
        setquestionViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
package com.example.soul_searching.ui.diary_safety;

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
import com.example.soul_searching.ui.diary_item.DiartItemViewModel;

public class DiarySafety extends Fragment {

    private DiarySafetyViewModel diarysafetyViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        diarysafetyViewModel =
                new ViewModelProvider(this).get(DiarySafetyViewModel.class);
        View root = inflater.inflate(R.layout.diary_item, container, false);
        final TextView textView = root.findViewById(R.id.diary_item);
        diarysafetyViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
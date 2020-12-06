package com.example.soul_searching.ui;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.soul_searching.R;
import com.example.soul_searching.Tools.LanKzy;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LanKzyPasswordEditor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LanKzyPasswordEditor extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View rootView;

    private EditText passwordInput;
    private EditText passwordConfirm;

    public LanKzyPasswordEditor() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LanKzyPasswordEditor.
     */
    // TODO: Rename and change types and number of parameters
    public static LanKzyPasswordEditor newInstance(String param1, String param2) {
        LanKzyPasswordEditor fragment = new LanKzyPasswordEditor();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_lankzy_password_editor, container, false);

        passwordInput = rootView.findViewById(R.id.password_input);
        passwordConfirm = rootView.findViewById(R.id.password_confirm);

        ColorStateList color = passwordInput.getTextColors();
        passwordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals(passwordConfirm.getText().toString())){
                    LanKzy.SetPassword(s.toString());
                    passwordInput.setTextColor(color);
                    passwordConfirm.setTextColor(color);
                }else{
                    passwordInput.setTextColor(Color.parseColor("#FFFF0000"));
                    passwordConfirm.setTextColor(Color.parseColor("#FFFF0000"));
                }
            }
        });

        passwordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals(passwordInput.getText().toString())){
                    LanKzy.SetPassword(s.toString());
                    passwordInput.setTextColor(color);
                    passwordConfirm.setTextColor(color);
                }else{
                    passwordInput.setTextColor(Color.parseColor("#FFFF0000"));
                    passwordConfirm.setTextColor(Color.parseColor("#FFFF0000"));
                }
            }
        });
        return rootView;
    }
}
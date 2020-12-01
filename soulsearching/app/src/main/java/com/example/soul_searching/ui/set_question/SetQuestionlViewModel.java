package com.example.soul_searching.ui.set_question;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SetQuestionlViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SetQuestionlViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is set_question fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
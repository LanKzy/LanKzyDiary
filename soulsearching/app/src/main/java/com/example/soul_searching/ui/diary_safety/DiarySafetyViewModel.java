package com.example.soul_searching.ui.diary_safety;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DiarySafetyViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DiarySafetyViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is diary_safety fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
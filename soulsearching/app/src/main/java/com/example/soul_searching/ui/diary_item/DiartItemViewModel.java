package com.example.soul_searching.ui.diary_item;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DiartItemViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DiartItemViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is diary_item fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
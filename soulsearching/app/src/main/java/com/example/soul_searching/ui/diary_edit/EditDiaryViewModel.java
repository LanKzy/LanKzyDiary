package com.example.soul_searching.ui.diary_edit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EditDiaryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EditDiaryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
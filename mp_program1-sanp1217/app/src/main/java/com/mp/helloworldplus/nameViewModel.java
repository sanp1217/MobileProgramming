package com.mp.helloworldplus;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class nameViewModel extends ViewModel {
    private MutableLiveData<String> name = new MutableLiveData<>();

    public LiveData<String> getName(){
        return this.name;
    }

    public void setName(String name){
        this.name.setValue(name);
    }
}

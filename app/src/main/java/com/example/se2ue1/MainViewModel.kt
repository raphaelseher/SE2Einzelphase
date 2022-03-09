package com.example.se2ue1

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    var textInput =  MutableLiveData<String>();
    var textOutput =  ObservableField<String>();

    fun didClickSend() {
       Log.v("MainViewModel", "DidClicKSend: ${textInput.value}");
        textOutput.set(textInput.value)
    }
}
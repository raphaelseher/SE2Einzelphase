package com.example.se2ue1

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.*
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.Socket
import java.net.URL

class MainViewModel : ViewModel() {
    var textInput = MutableLiveData<String>();
    var textOutput = ObservableField<String>();

    private val host = "se2-isys.aau.at";
    private val port = 53212;

    fun didClickSend() {
        Log.v("MainViewModel", "DidClicKSend: ${textInput.value}");

        val number = textInput.value ?: return;
        viewModelScope.launch {
            callServer(number)?.let {
                textOutput.set(it)
            }
        }
    }

    private suspend fun callServer(data: String): String? = withContext(Dispatchers.IO) {
        val socket = Socket(host, port)
        try {
            val input = DataInputStream(socket.getInputStream())
            val output = DataOutputStream(socket.getOutputStream())
            output.writeBytes(data + "\n")

            val returned = input.readLine()

            socket.close()

            return@withContext returned
        } catch (e: Exception) {
            e.message?.let { Log.w("MainViewModel", it) }
            return@withContext e.message;
        } finally {
            socket.close()
        }
    }
}
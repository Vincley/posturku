package com.capstone.posturku.ui.camera.mediaplayer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AudioViewModel : ViewModel() {
    private val _playAudioLiveData = MutableLiveData<Boolean>()
    val playAudioLiveData = _playAudioLiveData

    fun playAudio() {
//        _playAudioLiveData.value = true
        _playAudioLiveData.postValue(true)
    }

    fun stopAudio() {
//        _playAudioLiveData.value = false
        _playAudioLiveData.postValue(false)
    }
}
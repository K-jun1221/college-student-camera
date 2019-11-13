package com.example.student_camera.detail_photo

import android.app.Application
import androidx.lifecycle.*

class DetailPhotoViewModel(application: Application, uri: String) : AndroidViewModel(application) {

    private val uri = uri
    private val _imageUri = MutableLiveData<String>()
    val imageUri: LiveData<String>
        get() = _imageUri

    init {
        _imageUri.value = uri
    }
}

class SelectedPhotoViewModelFactory(private val application: Application, private val uri: String) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailPhotoViewModel::class.java)) {
            return DetailPhotoViewModel(application, uri) as T
        }
        throw IllegalArgumentException("Unewknown ViewModel class")
    }
}
package com.example.student_camera.selected_photos

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.student_camera.database.Photo
import com.example.student_camera.database.PhotoDatabaseDao
import kotlinx.coroutines.*

class SelectedPhotoViewModel(
    val database: PhotoDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        getAll()
    }

    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>>
        get() = _photos

    fun getAll() {
        uiScope.launch {
            val newPhotos = _getAll()
            if (newPhotos != null) {
                _photos.value = newPhotos
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private suspend fun _getAll(): List<Photo>? {
        var photos: List<Photo>? = null
        withContext(Dispatchers.IO) {
            photos = database.getAll()

            Log.d("photo_list in database access", photos!![0].uri)
        }
        return photos
    }
}

class SelectedPhotoViewModelFactory(
    private val dataSource: PhotoDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelectedPhotoViewModel::class.java)) {
            return SelectedPhotoViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unewknown ViewModel class")
    }
}
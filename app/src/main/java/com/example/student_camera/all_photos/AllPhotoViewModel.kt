package com.example.student_camera.all_photos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.student_camera.database.Photo
import com.example.student_camera.database.PhotoDatabaseDao
import kotlinx.coroutines.*

class AllPhotoViewModel(
    val database: PhotoDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        getLast()
    }

    private val _photo = MutableLiveData<Photo>()
    val photo: LiveData<Photo>
        get() = _photo

    fun getLast() {
        var photo: Photo? = null
        uiScope.launch {
            val newPhoto = _getLast()
            if (newPhoto != null) {
                _photo.value = newPhoto
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private suspend fun _getLast(): Photo? {
        var photo: Photo? = null
        withContext(Dispatchers.IO) {
            photo = database.getLast()
        }
        return photo
    }
}


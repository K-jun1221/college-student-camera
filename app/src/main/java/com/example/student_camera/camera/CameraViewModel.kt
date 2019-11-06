package com.example.student_camera.camera

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.student_camera.database.Photo
import com.example.student_camera.database.PhotoDatabaseDao
import kotlinx.coroutines.*

class CameraViewModelFactory(
    private val dataSource: PhotoDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CameraViewModel::class.java)) {
            return CameraViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

// viewModel
class CameraViewModel(
    val database: PhotoDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun insert(uri: String) {
        uiScope.launch {
            var newPhoto = Photo()
            newPhoto.uri = uri
            _insert(newPhoto)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private suspend fun _insert(photo: Photo) {
        withContext(Dispatchers.IO) {
            database.insert(photo)
        }
    }

}
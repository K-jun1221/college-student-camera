package com.example.student_camera.camera

import android.app.Application
import androidx.lifecycle.*
import com.example.student_camera.database.Photo
import com.example.student_camera.database.PhotoDatabaseDao
import kotlinx.coroutines.*
import java.util.*

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

    private val _lastPhoto = MutableLiveData<Photo>()
    val lastPhoto: LiveData<Photo>
        get() = _lastPhoto

    fun insert(uri: String) {
        uiScope.launch {
            val now = Date()
            // todo timeCellNum, dayCellNumを判別するコードが必要
            //            1: monday, 7: sunday
            val random = Random();
            var newPhoto = Photo(0, uri, random.nextInt(7) + 1, random.nextInt(7) + 1, now)

            _insert(newPhoto)
            _lastPhoto.value = newPhoto
        }
    }

    fun clear() {
        uiScope.launch {
            _clear()
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

    private suspend fun _clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

}
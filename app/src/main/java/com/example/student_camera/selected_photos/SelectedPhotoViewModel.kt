package com.example.student_camera.selected_photos

import android.app.Application
import androidx.lifecycle.*
import com.example.student_camera.database.Photo
import com.example.student_camera.database.PhotoDatabaseDao
import kotlinx.coroutines.*

class SelectedPhotoViewModel(
    val database: PhotoDatabaseDao,
    application: Application,
    selectedDay: Int,
    selectedTime: Int
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val selectedDay = selectedDay
    private val selectedTime = selectedTime

    init {
        initialize()
    }

    private val _photos = MutableLiveData<List<DataItem>>()
    val photos: LiveData<List<DataItem>>
        get() = _photos

    fun initialize() {
        uiScope.launch {
            val newPhotos = _getSelectedCell(selectedDay, selectedTime)
            if (newPhotos != null) {

//                TODO 撮った日時でまとめる
                _photos.value = listOf(DataItem.Header("test text")) + newPhotos.map { DataItem.PhotoItem(it) } + listOf(DataItem.Header("test text")) + newPhotos.map { DataItem.PhotoItem(it) }
            }
        }
    }

    private suspend fun _getSelectedCell(selectedTime: Int, selectedDay: Int): List<Photo>? {
        var newPhotos: List<Photo>? = null
        withContext(Dispatchers.IO) {
            newPhotos = database.getSelectedCell(selectedDay, selectedTime)
//            Log.d("photo_list in database access", photos!![0].uri)
        }
        return newPhotos
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

class SelectedPhotoViewModelFactory(
    private val dataSource: PhotoDatabaseDao,
    private val application: Application,
    private val selectedDay: Int,
    private val selectedTime: Int
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelectedPhotoViewModel::class.java)) {
            return SelectedPhotoViewModel(dataSource, application, selectedDay, selectedTime) as T
        }
        throw IllegalArgumentException("Unewknown ViewModel class")
    }
}
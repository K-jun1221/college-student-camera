package com.example.student_camera.all_photos

import android.app.Application
import androidx.lifecycle.*
import com.example.student_camera.database.Photo
import com.example.student_camera.database.PhotoDatabaseDao
import kotlinx.coroutines.*

class AllPhotoViewModel(
    val database: PhotoDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    var _all = MutableLiveData<List<DataItem>>()
    val all: LiveData<List<DataItem>>
        get() = _all

    var _allLast = MutableLiveData<Photo>()
    val allLast: LiveData<Photo>
        get() = _allLast

    init {
        getAll()
    }

    fun getAll() {
        uiScope.launch {
            _all.value = listOf(DataItem.Header("日曜日")) +_getAll().map { DataItem.PhotoItem(it) } + listOf(DataItem.Header("月曜日")) +_getAll().map { DataItem.PhotoItem(it) }
            _allLast.value = _getAllLast()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private suspend fun _getAll(): List<Photo> {
        lateinit var photos: List<Photo>
        withContext(Dispatchers.IO) {
            photos = database.getAll()
        }
        return photos
    }

    private suspend fun _getAllLast(): Photo? {
        var photo: Photo? = null
        withContext(Dispatchers.IO) {
            photo = database.getAllLast()
        }
        return photo
    }


    private suspend fun _getAll(dayNum: Int): List<Photo> {
        lateinit var photo: List<Photo>
        withContext(Dispatchers.IO) {
            photo = database.getAll()
        }
        return photo
    }
}


class AllPhotoViewModelFactory(
    private val dataSource: PhotoDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllPhotoViewModel::class.java)) {
            return AllPhotoViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unewknown ViewModel class")
    }
}

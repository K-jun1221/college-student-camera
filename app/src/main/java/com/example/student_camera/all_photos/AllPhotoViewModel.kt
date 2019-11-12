package com.example.student_camera.all_photos

import android.app.Application
import android.util.Log
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


    var _allLastPhoto = MutableLiveData<Photo>()
    val allLastPhoto: LiveData<Photo>
        get() = _allLastPhoto

    var _sundayLastPhoto = MutableLiveData<List<Photo>>()
    val sundayLastPhoto: LiveData<List<Photo>>
        get() = _sundayLastPhoto

    var _mondayLastPhoto = MutableLiveData<List<Photo>>()
    val mondayLastPhoto: LiveData<List<Photo>>
        get() = _mondayLastPhoto

    var _tuesdayLastPhoto = MutableLiveData<List<Photo>>()
    val tuesdayLastPhoto: LiveData<List<Photo>>
        get() = _tuesdayLastPhoto

    var _wednesdayLastPhoto = MutableLiveData<List<Photo>>()
    val wednesdayLastPhoto: LiveData<List<Photo>>
        get() = _wednesdayLastPhoto

    var _thursdayLastPhoto = MutableLiveData<List<Photo>>()
    val thursdayLastPhoto: LiveData<List<Photo>>
        get() = _thursdayLastPhoto

    var _fridayLastPhoto = MutableLiveData<List<Photo>>()
    val fridayLastPhoto: LiveData<List<Photo>>
        get() = _fridayLastPhoto

    var _saturdayLastPhoto = MutableLiveData<List<Photo>>()
    val saturdayLastPhoto: LiveData<List<Photo>>
        get() = _saturdayLastPhoto

    init {
        getLast()
    }

    fun getLast() {
        uiScope.launch {
            var newPhoto = _getAllLast()
            _allLastPhoto.value = newPhoto
            _mondayLastPhoto.value = _getSelectedLast(1)
            _tuesdayLastPhoto.value = _getSelectedLast(2)
            _wednesdayLastPhoto.value = _getSelectedLast(3)
            _thursdayLastPhoto.value = _getSelectedLast(4)
            _fridayLastPhoto.value = _getSelectedLast(5)
            _saturdayLastPhoto.value = _getSelectedLast(6)
            _sundayLastPhoto.value = _getSelectedLast(7)
        }
    }

    fun testFunc() {
        Log.d("testFunc", "testFunc called")
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



    private suspend fun _getSelectedLast(dayNum: Int): List<Photo> {
        lateinit var photo: List<Photo>
        withContext(Dispatchers.IO) {
            photo = database.getSelectedLast(dayNum)
        }
        return photo
    }
}


class AllPhotoViewModelFactory(
    private val dataSource: PhotoDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllPhotoViewModel::class.java)) {
            return AllPhotoViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unewknown ViewModel class")
    }
}


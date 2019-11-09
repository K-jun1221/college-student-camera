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

    init {
//        _allLastPhotoUri.value = "testVaue"
        getLast()
    }

    fun getLast() {
        uiScope.launch {
            var newPhoto = _getAllLast()
            if (newPhoto != null) {
                _allLastPhoto.value = newPhoto
//                Log.d("newPhoto", allLastPhotoUri)
            } else {
                Log.d("newPhoto", "null")
            }
        }
    }

    fun testFunc() {
        Log.d("testFunc", "testFunc called")
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private suspend fun _getAllLast(): Photo? {
        var photo: Photo? = null
        withContext(Dispatchers.IO) {
            photo = database.getAllLast()
        }
        return photo
    }

    private suspend fun _getSelectedLast(dayNum: Int, timeNum: Int): Photo? {
        var photo: Photo? = null
        withContext(Dispatchers.IO) {
            photo = database.getSelectedLast(dayNum, timeNum)
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


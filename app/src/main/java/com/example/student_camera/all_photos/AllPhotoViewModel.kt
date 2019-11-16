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

    var _all = MutableLiveData<List<DataItem>>()
    val all: LiveData<List<DataItem>>
        get() = _all

    init {
        getAll()
    }

    fun getAll() {
        uiScope.launch {
            var friday: List<DataItem> = listOf(DataItem.Header("金曜日"))
            for (i in 1..5) {
                val item = DataItem.PhotoItem(_getSelectedLast(5, i))
                if (0 < item.photo.photoId) {
                    friday += listOf(item)
                }
            }

            Log.i("friday", friday.toString())

            var tempList: List<DataItem> = listOf()

            if (friday.size != 1) {
                tempList = tempList + friday
            }

            _all.value = tempList

        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private suspend fun _getAllLast(): Photo {
        lateinit var photo: Photo
        withContext(Dispatchers.IO) {
            val selectedPhoto = database.getAllLast()
            if (selectedPhoto != null) {
                photo = selectedPhoto
            } else {
                photo = Photo(0, "", 0, 0)
            }
        }
        return photo
    }

    private suspend fun _getSelectedLast(dayNum: Int, timeNum: Int): Photo {
        lateinit var photo: Photo
        withContext(Dispatchers.IO) {
            val selectedPhoto = database.getSelectedCellLast(dayNum, timeNum)
            if (selectedPhoto != null) {
                photo = selectedPhoto
            } else {
                photo = Photo(0, "", 0, 0)
            }
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

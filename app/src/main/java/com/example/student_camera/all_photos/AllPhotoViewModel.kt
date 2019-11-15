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
            //            日曜日
            var sunday: List<DataItem> = listOf(DataItem.Header("日曜日"))
            for (i in 1..5) {
                val item = DataItem.PhotoItem(_getSelectedLast(7, i))
                if (0 < item.photo.photoId) {
                    sunday += listOf(item)
                }
            }
            var monday: List<DataItem> = listOf(DataItem.Header("月曜日"))
            for (i in 1..5) {
                val item = DataItem.PhotoItem(_getSelectedLast(1, i))
                if (0 < item.photo.photoId) {
                    monday += listOf(item)
                }
            }
            var tuesday: List<DataItem> = listOf(DataItem.Header("火曜日"))
            for (i in 1..5) {
                val item = DataItem.PhotoItem(_getSelectedLast(2, i))
                if (0 < item.photo.photoId) {
                    tuesday += listOf(item)
                }
            }
            var wednesday: List<DataItem> = listOf(DataItem.Header("水曜日"))
            for (i in 1..5) {
                val item = DataItem.PhotoItem(_getSelectedLast(3, i))
                if (0 < item.photo.photoId) {
                    wednesday += listOf(item)
                }
            }
            var thursday: List<DataItem> = listOf(DataItem.Header("木曜日"))
            for (i in 1..5) {
                val item = DataItem.PhotoItem(_getSelectedLast(4, i))
                if (0 < item.photo.photoId) {
                    thursday += listOf(item)
                }
            }
            var friday: List<DataItem> = listOf(DataItem.Header("金曜日"))
            for (i in 1..5) {
                val item = DataItem.PhotoItem(_getSelectedLast(5, i))
                if (0 < item.photo.photoId) {
                    friday += listOf(item)
                }
            }
            var saturday: List<DataItem> = listOf(DataItem.Header("土曜日"))
            for (i in 1..5) {
                val item = DataItem.PhotoItem(_getSelectedLast(6, i))
                if (0 < item.photo.photoId) {
                    saturday += listOf(item)
                }
            }

            Log.i("monday", monday.toString())
            Log.i("friday", friday.toString())

            var tempList: List<DataItem> =
                listOf(DataItem.PhotoItem(_getAllLast())) + listOf(DataItem.PhotoItem(_getAllLast()))
//            if (1 < sunday.size) {
//                tempList = tempList + sunday
//            }
//            if (1 < monday.size) {
//                tempList = tempList + monday
//            }
//
//            if (tuesday.size != 1) {
//                tempList = tempList + tuesday
//            }
//
//            if (wednesday.size != 1) {
//                tempList = tempList + wednesday
//            }
//
//            if (thursday.size != 1) {
//                tempList = tempList + thursday
//            }

            if (friday.size != 1) {
                tempList = tempList + friday
            }
//            if (saturday.size != 1) {
//                tempList = tempList + saturday
//            }

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

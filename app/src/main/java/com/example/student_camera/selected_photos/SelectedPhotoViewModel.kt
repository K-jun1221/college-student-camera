package com.example.student_camera.selected_photos

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.student_camera.database.Photo
import com.example.student_camera.database.PhotoDatabaseDao
import kotlinx.coroutines.*
import java.util.*

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
            var selectedPhotos: List<DataItem> = listOf()

            if (selectedDay == -1) {
                if (selectedTime == -1) {
                    // 全ての画像
                    selectedPhotos = _getAll().map { DataItem.PhotoItem(it) }
                } else {
                    // 時間外
                    selectedPhotos = _getSelectedDay(selectedTime).map { DataItem.PhotoItem(it) }
                }
            } else {
                selectedPhotos =
                    _getSelectedCell(selectedTime, selectedDay).map { DataItem.PhotoItem(it) }
            }

            var tempList: List<DataItem> = listOf()
            var monthMemo = 0
            var dayMemo = 0
            val numToDay: Map<Int, String> =
                mapOf(1 to "日", 2 to "月", 3 to "火", 4 to "水", 5 to "木", 6 to "金", 7 to "土")

            selectedPhotos.forEach({
                val c = Calendar.getInstance()
                c.time = it.photo.createdAt
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DATE)

                Log.d("initialize month", month.toString())
                Log.d("initialize day", day.toString())

                if (month != monthMemo || day != dayMemo) {
                    val label =
                        month.toString() + "月" + day.toString() + "(" + numToDay.get(c.get(Calendar.DAY_OF_WEEK)) + ")"
                    tempList += listOf<DataItem>(DataItem.Header(label))
                    monthMemo = month
                    dayMemo = day
                }
                tempList += listOf<DataItem>(it)
                tempList
                Log.d("initialize it", it.toString())
                Log.d("initialize monthMemo", monthMemo.toString())
                Log.d("initialize dayMemo", dayMemo.toString())
            })


            _photos.value = tempList
        }
    }

    private suspend fun _getSelectedCell(selectedTime: Int, selectedDay: Int): List<Photo> {
        return withContext(Dispatchers.IO) {
            database.getSelectedCell(selectedDay, selectedTime)
        }
    }

    private suspend fun _getSelectedDay(selectedDay: Int): List<Photo> {
        return withContext(Dispatchers.IO) {
            database.getSelectedDay(selectedDay)
        }
    }

    private suspend fun _getAll(): List<Photo> {
        return withContext(Dispatchers.IO) {
            database.getAll()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
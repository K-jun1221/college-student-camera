package com.example.student_camera.camera

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.student_camera.database.Photo
import com.example.student_camera.database.PhotoDatabaseDao
import com.example.student_camera.database.TimeSchedule
import com.example.student_camera.database.TimeScheduleDatabaseDao
import kotlinx.coroutines.*
import java.util.*


//class CameraViewModelFactory(
//    private val dataSourcePhoto: PhotoDatabaseDao,
//    private val dataSourceTimeSchedule: TimeScheduleDatabaseDao,
//    private val application: Application
//) : ViewModelProvider.Factory {
//    @Suppress("unchecked_cast")
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(CameraViewModel::class.java)) {
//            return CameraViewModel(dataSourcePhoto, dataSourceTimeSchedule, application) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}

// viewModel
class CameraViewModel(
    val databasePhoto: PhotoDatabaseDao,
    val databaseTimeSchedule: TimeScheduleDatabaseDao
//    application: Application
) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _lastPhoto = MutableLiveData<Photo>()
    val lastPhoto: LiveData<Photo>
        get() = _lastPhoto

    private val _timeSchedules = MutableLiveData<List<TimeSchedule>>()
    val timeSchedules: LiveData<List<TimeSchedule>>
        get() = _timeSchedules

    init {
        setTimeSchedule()
    }

    fun setTimeSchedule() {
        uiScope.launch {
            _timeSchedules.value = _getAll()
            Log.i("_timeSchedules", _timeSchedules.value.toString())
        }
    }

    fun insert(uri: String) {
        uiScope.launch {
            val now = Date()
            val hour = now.hours
            val minute = now.minutes

            val c = Calendar.getInstance()
            c.time = now

            // Sunday: 1, SaturDay: 7っぽいので
            val dayOfWeek = c.get(Calendar.DAY_OF_WEEK)
            var dayCell = 0
            if (dayOfWeek == 1) {
                dayCell = 7
            } else {
                dayCell = dayOfWeek - 1
            }

            var timeCell = 0



            Log.d("timeSchedules1", _getAll().toString())
            Log.d("timeSchedules2", _timeSchedules.value.toString())
            Log.d("timeSchedules3", timeSchedules.value.toString())
            timeSchedules.value?.forEach {

                Log.d("timeSchedules", it.toString())
                val startAt = it.start_at
                val endAt = it.end_at
                val nowStr = hour.toString() + ":" + minute.toString()
                if (
                    timeFirstIsMoreOrEqual(nowStr, startAt) &&
                    timeFirstIsMoreOrEqual(endAt, nowStr)
                ) {
                    timeCell = it.num
                }
            }

            var newPhoto = Photo(0, uri, dayCell, timeCell, now)
            Log.i("newPhoto", newPhoto.toString())
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
            databasePhoto.insert(photo)
        }
    }

    private suspend fun _getAll(): List<TimeSchedule> {
        lateinit var ts: List<TimeSchedule>
        withContext(Dispatchers.IO) {
            ts = databaseTimeSchedule.getAll()
        }

        return ts
    }

    private suspend fun _clear() {
        withContext(Dispatchers.IO) {
            databasePhoto.clear()
        }
    }

}

fun timeFirstIsMoreOrEqual(a: String, b: String): Boolean {
    val splitedStrA = a.split(":")
    val hourA = splitedStrA[0].toInt()
    val minA = splitedStrA[1].toInt()

    val splitedStrB = b.split(":")
    val hourB = splitedStrB[0].toInt()
    val minB = splitedStrB[1].toInt()

    if (hourA < hourB) {
        return false
    } else if (hourB < hourA) {
        return true
    } else {
        return minB <= minA
    }
}
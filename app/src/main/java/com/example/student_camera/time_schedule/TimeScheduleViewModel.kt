package com.example.student_camera.time_schedule

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.student_camera.database.TimeSchedule
import com.example.student_camera.database.TimeScheduleDatabaseDao
import kotlinx.coroutines.*

class TimeScheduleViewModel(
    val database: TimeScheduleDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val _timeSchedules = MutableLiveData<List<TimeSchedule>>()
    val timeSchedules: LiveData<List<TimeSchedule>>
        get() = _timeSchedules

    init {
        setTimeSchedule()
    }

    fun updateTimeSchedule(num: Int, startAt: String, endAt: String) {
        _timeSchedules.value = _timeSchedules.value?.map {
            if (it.num == num) {
                TimeSchedule(it.timeScheduleId, num, startAt, endAt)
            } else {
                it
            }
        }

    }

    fun saveTimeSchedule() {
        uiScope.launch {
            _timeSchedules.value?.map {
                //                TODO Error Handling
                _update(it)
            }
        }
    }

    fun setTimeSchedule() {
        uiScope.launch {
            if (_get(1) == null) {
                _insert(TimeSchedule(0, 1, "8:45", "10:15"))
            }
            if (_get(2) == null) {
                _insert(TimeSchedule(0, 2, "10:30", "12:00"))
            }
            if (_get(3) == null) {
                _insert(TimeSchedule(0, 3, "13:00", "14:30"))
            }
            if (_get(4) == null) {
                _insert(TimeSchedule(0, 4, "14:45", "16:15"))
            }
            if (_get(5) == null) {
                _insert(TimeSchedule(0, 5, "16:30", "18:00"))
            }
            _timeSchedules.value = _getAll()
        }
    }

    private suspend fun _get(num: Int): TimeSchedule? {
        var time_schedule: TimeSchedule? = null
        withContext(Dispatchers.IO) {
            time_schedule = database.get(num)
        }
        return time_schedule
    }

    private suspend fun _getAll(): List<TimeSchedule> {
        lateinit var ts: List<TimeSchedule>
        withContext(Dispatchers.IO) {
            ts = database.getAll()
            Log.i("_getAll", ts.toString())
        }
        return ts
    }

    private suspend fun _insert(ts: TimeSchedule) {
        withContext(Dispatchers.IO) {
            database.insert(ts)
        }
    }

    private suspend fun _update(ts: TimeSchedule) {

        withContext(Dispatchers.IO) {
            database.update(ts)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

class TimeScheduleViewModelFactory(
    private val dataSource: TimeScheduleDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimeScheduleViewModel::class.java)) {
            return TimeScheduleViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
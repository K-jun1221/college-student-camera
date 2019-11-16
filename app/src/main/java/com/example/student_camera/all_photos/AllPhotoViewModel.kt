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

    init {
        getAll()
    }

    fun getAll() {
        uiScope.launch {

            var tempList: List<DataItem> = listOf()

            // 全ての写真
            var all = listOf(DataItem.PhotoItem(_getAllLast(), "全ての画像", -1, -1))
            // 時間外の写真
            var execlude = listOf(DataItem.PhotoItem(_getSelectedDayLast(0), "時間外", -1, 0))
            tempList += all + execlude

            val dayOfWeeks =
                listOf<String>("日") + listOf<String>("月") + listOf<String>("火") + listOf<String>("水") + listOf<String>(
                    "木"
                ) + listOf<String>("金") + listOf<String>("土")
            val dayToNum: Map<String, Int> =
                mapOf("日" to 7, "月" to 1, "火" to 2, "水" to 3, "木" to 4, "金" to 5, "土" to 6)

            dayOfWeeks.forEach({
                var day: List<DataItem> = listOf(DataItem.Header(it + "曜日"))
                for (i in 1..5) {
                    val item =
                        DataItem.PhotoItem(
                            _getSelectedLast(dayToNum.get(it)!!, i),
                            it + "曜" + i.toString() + "限",
                            dayToNum.get(it)!!,
                            i
                        )
                    if (0 < item.photo.photoId) {
                        day += listOf(item)
                    }
                }

                if (day.size != 1) {
                    tempList = tempList + day
                }
            })

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

    private suspend fun _getSelectedDayLast(timeNum: Int): Photo {
        lateinit var photo: Photo
        withContext(Dispatchers.IO) {
            val selectedPhoto = database.getSelectedDayLast(timeNum)
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

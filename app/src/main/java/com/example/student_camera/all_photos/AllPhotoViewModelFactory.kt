package com.example.student_camera.all_photos

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.student_camera.database.PhotoDatabaseDao

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
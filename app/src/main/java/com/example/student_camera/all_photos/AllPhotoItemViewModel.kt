package com.example.student_camera.all_photos

import androidx.lifecycle.ViewModel
import com.example.student_camera.database.Photo

class AllPhotoItemViewModel(position: Int, photo: Photo) :
    ViewModel() {

    val position = position
//    val right_margin = "20dp"
//    val left_margin = "0dp"
    val right_margin = true
    val left_margin = false


    val photo = photo
}

//class AllPhotoItemViewModelFactory(
//    private val application: Application,
//    private val position: Int,
//    private val photo: Photo
//) : ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(AllPhotoItemViewModel::class.java)) {
//            return AllPhotoItemViewModel(application, position, photo) as T
//        }
//        throw IllegalArgumentException("Unewknown ViewModel class")
//    }
//}

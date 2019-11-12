/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.student_camera

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.student_camera.all_photos.AllPhotoAdapter
import com.example.student_camera.database.Photo
import com.example.student_camera.selected_photos.DataItem
import com.example.student_camera.selected_photos.SelectedPhotoAdapter

@BindingAdapter("listData")
fun bindRecyclerViewSelectedPhoto(recyclerView: RecyclerView, data: List<DataItem>?) {
    val adapter = recyclerView.adapter as SelectedPhotoAdapter
    adapter.submitList(data)
}

@BindingAdapter("listDataAllPhoto")
fun bindRecyclerViewAllPhoto(recyclerView: RecyclerView, data: List<Photo>?) {
    val adapter = recyclerView.adapter as AllPhotoAdapter
    adapter.submitList(data)
}


@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = Uri.parse(imgUrl)
        Glide.with(imgView.context)
                .load(imgUri)
                .apply(RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image))
                .into(imgView)
    }
}

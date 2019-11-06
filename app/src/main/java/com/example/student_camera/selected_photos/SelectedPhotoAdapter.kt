package com.example.student_camera.selected_photos

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.student_camera.database.Photo
import com.example.student_camera.databinding.FragmentSelectedPhotoItemBinding

class SelectedPhotoAdapter(): ListAdapter<Photo, SelectedPhotoAdapter.ViewHolder>(PhotoDiffCallback())  {
    // required method
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    // required method
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: FragmentSelectedPhotoItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Photo) {

            Log.d("viewHolder", "setURI")
            binding.imageView.setImageURI(Uri.parse(item.uri))
            Log.d("viewHolder", "setedURI")
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentSelectedPhotoItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class PhotoDiffCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.photoId == newItem.photoId
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }
}
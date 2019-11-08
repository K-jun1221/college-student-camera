package com.example.student_camera.selected_photos

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.student_camera.database.Photo
import com.example.student_camera.databinding.FragmentSelectedPhotoItemBinding

class SelectedPhotoAdapter(): ListAdapter<Photo, SelectedPhotoAdapter.PhotoViewHolder>(PhotoDiffCallback())  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        Log.d("onCreateViewHolder", "onCreateViewHolder called")
        val viewHolder = PhotoViewHolder(FragmentSelectedPhotoItemBinding.inflate(LayoutInflater.from(parent.context)))
        Log.d("onCreateViewHolder", "onCreateViewHolder called")
        return viewHolder
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        Log.d("onBindViewHolder", "onBindViewHolder called")
        val item = getItem(position)
        holder.bind(item)
        Log.d("onBindViewHolder", "onBindViewHolder called")
    }

    class PhotoViewHolder constructor(val binding: FragmentSelectedPhotoItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Photo) {
            Log.d("PhotoViewHolder.bind", item.uri)

            binding.viewModel = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): PhotoViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentSelectedPhotoItemBinding.inflate(layoutInflater, parent, false)
                return PhotoViewHolder(binding)
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
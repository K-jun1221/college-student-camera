package com.example.student_camera.selected_photos

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.student_camera.R
import com.example.student_camera.database.Photo
import com.example.student_camera.databinding.FragmentSelectedPhotoItemBinding
import java.util.*

class SelectedPhotoAdapter() : ListAdapter<DataItem, RecyclerView.ViewHolder>(PhotoDiffCallback()) {

    val ITEM_VIEW_TYPE_HEADER = 0
    val ITEM_VIEW_TYPE_ITEM = 1

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.PhotoItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> PhotoViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PhotoViewHolder -> {
                val item = getItem(position) as DataItem.PhotoItem
                holder.bind(item.photo)
            }
        }
    }

    class PhotoViewHolder constructor(val binding: FragmentSelectedPhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Photo) {
            binding.viewModel = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): PhotoViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    FragmentSelectedPhotoItemBinding.inflate(layoutInflater, parent, false)
                return PhotoViewHolder(binding)
            }
        }
    }

    class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view =
                    layoutInflater.inflate(R.layout.fragment_selected_photo_header, parent, false)

                view.findViewById<TextView>(R.id.date_header).text = "10月12日(月)"
                return TextViewHolder(view)
            }
        }
    }
}

class PhotoDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

sealed class DataItem {
    abstract val id: Long

    data class PhotoItem(val photo: Photo) : DataItem() {
        override val id = photo.photoId
    }

    data class Header(val text: String) : DataItem() {
        override val id = 10000 + Random().nextLong()

    }
}
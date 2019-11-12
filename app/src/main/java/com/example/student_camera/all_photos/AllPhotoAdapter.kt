package com.example.student_camera.all_photos

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.student_camera.R
import com.example.student_camera.database.Photo
import com.example.student_camera.databinding.FragmentAllPhotoItemBinding
import java.util.*


class AllPhotoAdapter() : ListAdapter<DataItem, RecyclerView.ViewHolder>(PhotoDiffCallback()) {

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
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> PhotoViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PhotoViewHolder -> {
                val item = getItem(position) as DataItem.PhotoItem
                holder.bind(item.photo, position, "日曜")
            }
        }
    }

    class PhotoViewHolder constructor(val binding: FragmentAllPhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Photo, position: Int, dayName: String) {
            binding.viewModel = AllPhotoItemViewModel(position, item)
            binding.labelText.text = dayName + (position + 1).toString() + "限"
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): PhotoViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentAllPhotoItemBinding.inflate(layoutInflater, parent, false)
                binding.imageView.setClipToOutline(true)
                binding.imageView.setOnClickListener({view ->
                    view.findNavController().navigate(AllPhotoFragmentDirections.actionAllPhotoFragmentToSelectedPhotoFragment(1, 1))
                })
                return PhotoViewHolder(binding)
            }
        }
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view =
                    layoutInflater.inflate(R.layout.fragment_all_photo_header, parent, false)
                return HeaderViewHolder(view)
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

// TODO まとめるためにコード自体は残しておく
//class CustomItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
//
//    override fun getItemOffsets(
//        outRect: Rect,
//        view: View,
//        parent: RecyclerView,
//        state: RecyclerView.State
//    ) {
//        val position = parent.getChildAdapterPosition(view)
//
//        outRect.top = if (position == 0 || position == 1) space else space / 2
//        outRect.left = if (position % 2 == 1) space / 2 else space
//        outRect.right = if (position % 2 == 0) space / 2 else space
//        outRect.bottom = if (position - 1 == state.getItemCount()) space else space / 2
//    }
//
//    companion object {
//
//        fun createDefaultDecoration(): CustomItemDecoration {
//            return CustomItemDecoration(85)
//        }
//    }
//}

sealed class DataItem {
    abstract val id: Long

    data class PhotoItem(val photo: Photo) : DataItem() {
        override val id = photo.photoId
    }

    data class Header(val text: String) : DataItem() {
        override val id = 10000 + Random().nextLong()

    }
}
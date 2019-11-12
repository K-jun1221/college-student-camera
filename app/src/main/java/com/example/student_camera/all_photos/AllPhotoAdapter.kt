package com.example.student_camera.all_photos

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.student_camera.database.Photo
import com.example.student_camera.databinding.FragmentAllPhotoItemBinding


class AllPhotoAdapter(dayName: String) : ListAdapter<Photo, AllPhotoAdapter.PhotoViewHolder>(PhotoDiffCallback()) {

    val dayName = dayName
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position, dayName)
    }

    class PhotoViewHolder constructor(val binding: FragmentAllPhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Photo, position: Int, dayName: String) {
            binding.viewModel = item
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
}

class PhotoDiffCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.photoId == newItem.photoId
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }
}

class CustomItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)

        outRect.top = if (position == 0 || position == 1) space else space / 2
        outRect.left = if (position % 2 == 1) space / 2 else space
        outRect.right = if (position % 2 == 0) space / 2 else space
        outRect.bottom = if (position - 1 == state.getItemCount()) space else space / 2
    }

    companion object {

        fun createDefaultDecoration(): CustomItemDecoration {
            return CustomItemDecoration(85)
        }
    }
}
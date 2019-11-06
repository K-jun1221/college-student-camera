package com.example.student_camera.selected_photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.student_camera.R
import com.example.student_camera.database.AppDatabase
import com.example.student_camera.databinding.FragmentSelectedPhotoBinding

class SelectedPhotoFragment : Fragment() {
    private lateinit var binding: FragmentSelectedPhotoBinding
    private lateinit var viewModel: SelectedPhotoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_selected_photo, container, false)

        binding.icAllPhotos.setOnClickListener{ view: View ->
            view.findNavController().navigate(R.id.action_selectedPhotoFragment_to_allPhotoFragment)
        }

        val adapter = SelectedPhotoAdapter()

        binding.selectedImages.adapter = adapter
        binding.selectedImages.layoutManager = GridLayoutManager(activity, 3)

        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getPhotoInstance(application).photoDatabaseDao()
        val viewModelFactory = SelectedPhotoViewModelFactory(dataSource, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SelectedPhotoViewModel::class.java)

        // adapterにデータを流し込む
        viewModel.photos.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root


    }
}
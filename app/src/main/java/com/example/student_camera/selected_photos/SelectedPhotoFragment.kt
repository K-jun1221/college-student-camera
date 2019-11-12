package com.example.student_camera.selected_photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.student_camera.R
import com.example.student_camera.database.AppDatabase
import com.example.student_camera.databinding.FragmentSelectedPhotoBinding

class SelectedPhotoFragment : Fragment() {
    private lateinit var binding: FragmentSelectedPhotoBinding
    private lateinit var viewModel: SelectedPhotoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getPhotoInstance(application).photoDatabaseDao()
        val args = SelectedPhotoFragmentArgs.fromBundle(arguments!!)

        val viewModelFactory = SelectedPhotoViewModelFactory(dataSource, application, args.selectedDay, args.selectedTime)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SelectedPhotoViewModel::class.java)


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_selected_photo, container, false)
        binding.viewModel = viewModel

        val adapter = SelectedPhotoAdapter()
        binding.recyclePhotos.adapter = adapter



        viewModel.photos.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }
}
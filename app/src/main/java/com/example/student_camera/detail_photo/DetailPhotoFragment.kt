package com.example.student_camera.detail_photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.student_camera.R
import com.example.student_camera.databinding.FragmentDetailBinding

class DetailPhotoFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val args = DetailPhotoFragmentArgs.fromBundle(arguments!!)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = SelectedPhotoViewModelFactory(application, args.uri)
        binding.viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailPhotoViewModel::class.java)
        binding.lifecycleOwner = this

        return binding.root
    }
}
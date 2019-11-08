package com.example.student_camera.all_photos

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.student_camera.R
import com.example.student_camera.database.AppDatabase
import com.example.student_camera.databinding.FragmentAllPhotoBinding

class AllPhotoFragment : Fragment() {
    private lateinit var binding: FragmentAllPhotoBinding
    private lateinit var viewModel: AllPhotoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_photo, container, false)
        binding.icCamera.setOnClickListener{ view: View ->
            view.findNavController().navigate(R.id.action_allPhotoFragment_to_cameraFragment)
        }

        binding.allPhotos.setOnClickListener{ view: View ->
            view.findNavController().navigate(R.id.action_allPhotoFragment_to_selectedPhotoFragment)
//            performFileSearch()
        }

        binding.excludedPhotos.setOnClickListener{ view: View ->
            view.findNavController().navigate(R.id.action_allPhotoFragment_to_selectedPhotoFragment)
        }

        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getPhotoInstance(application).photoDatabaseDao()
        val viewModelFactory = AllPhotoViewModelFactory(dataSource, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AllPhotoViewModel::class.java)

        binding.lifecycleOwner = this

        viewModel.photo.observe(this, Observer { newPhoto ->
            binding.allPhotos.setImageURI(Uri.parse(newPhoto.uri))
        })


        return binding.root
    }

    fun performFileSearch() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }

        startActivityForResult(intent, READ_REQUEST_CODE)
    }

    companion object {
        private const val READ_REQUEST_CODE: Int = 42
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            resultData?.data?.also { uri ->
                Log.i("onActivityResult", "Uri: $uri")
//                showImage(uri)
            }
        }
    }

}
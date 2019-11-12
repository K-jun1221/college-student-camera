package com.example.student_camera.all_photos

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
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

        (activity as AppCompatActivity).supportActionBar!!.show()
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_photo, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getPhotoInstance(application).photoDatabaseDao()
        val viewModelFactory = AllPhotoViewModelFactory(dataSource, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AllPhotoViewModel::class.java)

        binding.viewModel = viewModel

        binding.allPhoto.setClipToOutline(true)
        binding.execludedPhoto.setClipToOutline(true)

        binding.allPhoto.setOnClickListener({view ->
            view.findNavController().navigate(AllPhotoFragmentDirections.actionAllPhotoFragmentToSelectedPhotoFragment(1, 1))
        })

        binding.execludedPhoto.setOnClickListener({view ->
            view.findNavController().navigate(AllPhotoFragmentDirections.actionAllPhotoFragmentToSelectedPhotoFragment(1, 1))
        })

        binding.lifecycleOwner = this

        val adapterSun = AllPhotoAdapter("日曜")
        val adapterMon = AllPhotoAdapter("月曜")
        val adapterTue = AllPhotoAdapter("火曜")
        val adapterWed = AllPhotoAdapter("水曜")
        val adapterThu = AllPhotoAdapter("木曜")
        val adapterFri = AllPhotoAdapter("金曜")
        val adapterSat = AllPhotoAdapter("土曜")
        binding.sundayRecycle.adapter = adapterSun
        binding.mondayRecycle.adapter = adapterMon
        binding.tuesdayRecycle.adapter = adapterTue
        binding.wednesdayRecycle.adapter = adapterWed
        binding.thursdayRecycle.adapter = adapterThu
        binding.fridayRecycle.adapter = adapterFri
        binding.saturdayRecycle.adapter = adapterSat

        viewModel.sundayLastPhoto.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapterSun.submitList(it)
            }
        })

        viewModel.sundayLastPhoto.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.size != 0) {
                    binding.sunday.visibility = View.VISIBLE
                }
                adapterMon.submitList(it)
            }
        })

        viewModel.mondayLastPhoto.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.size != 0) {
                    binding.monday.visibility = View.VISIBLE
                }
                adapterTue.submitList(it)
            }
        })

        viewModel.tuesdayLastPhoto.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.size != 0) {
                    binding.tuesday.visibility = View.VISIBLE
                }
                adapterTue.submitList(it)
            }
        })

        viewModel.wednesdayLastPhoto.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.size != 0) {
                    binding.wednesday.visibility = View.VISIBLE
                }
                adapterWed.submitList(it)
            }
        })

        viewModel.thursdayLastPhoto.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.size != 0) {
                    binding.thursday.visibility = View.VISIBLE
                }
                adapterThu.submitList(it)
            }
        })

        viewModel.fridayLastPhoto.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.size != 0) {
                    binding.friday.visibility = View.VISIBLE
                }
                adapterFri.submitList(it)
            }
        })

        viewModel.saturdayLastPhoto.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.size != 0) {
                    binding.saturday.visibility = View.VISIBLE
                }
                adapterSat.submitList(it)
            }
        })

        binding.sundayRecycle.addItemDecoration(CustomItemDecoration.createDefaultDecoration())
        binding.mondayRecycle.addItemDecoration(CustomItemDecoration.createDefaultDecoration())
        binding.tuesdayRecycle.addItemDecoration(CustomItemDecoration.createDefaultDecoration())
        binding.wednesdayRecycle.addItemDecoration(CustomItemDecoration.createDefaultDecoration())
        binding.thursdayRecycle.addItemDecoration(CustomItemDecoration.createDefaultDecoration())
        binding.fridayRecycle.addItemDecoration(CustomItemDecoration.createDefaultDecoration())
        binding.saturdayRecycle.addItemDecoration(CustomItemDecoration.createDefaultDecoration())

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_all_photo, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.toString() == "Camera") {
            view?.findNavController()?.navigate(R.id.action_allPhotoFragment_to_cameraFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val READ_REQUEST_CODE: Int = 42
    }
}

data class MyName(var name: String = "", var nickname: String = "")
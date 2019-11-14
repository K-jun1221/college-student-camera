package com.example.student_camera.all_photos

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
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
        binding.lifecycleOwner = this

        val adapter = AllPhotoAdapter()
        binding.recycleView.adapter = adapter
//        TODO まとめ用コード
//        binding.recycleView.addItemDecoration(CustomItemDecoration.createDefaultDecoration())

        val manager = GridLayoutManager(activity, 2)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    adapter.ITEM_VIEW_TYPE_HEADER -> 2
                    adapter.ITEM_VIEW_TYPE_ITEM -> 1
                    else -> 0
                }
            }
        }
        binding.recycleView.layoutManager = manager

        activity?.setTitle("講義一覧")

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_all_photo, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.toString() == "Camera") {
            view?.findNavController()?.navigate(AllPhotoFragmentDirections.actionAllPhotoFragmentToCameraFragment())
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val READ_REQUEST_CODE: Int = 42
    }
}

data class MyName(var name: String = "", var nickname: String = "")
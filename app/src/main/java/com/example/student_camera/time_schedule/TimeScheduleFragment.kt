package com.example.student_camera.time_schedule

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.student_camera.R
import com.example.student_camera.database.AppDatabase
import com.example.student_camera.databinding.FragmentTimeScheduleBinding

class TimeScheduleFragment: Fragment() {
    private lateinit var binding: FragmentTimeScheduleBinding
    private lateinit var viewModel: TimeScheduleViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_time_schedule, container, false)

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowHomeEnabled(true);
        (activity as AppCompatActivity).supportActionBar!!.setHomeButtonEnabled(true);

        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getTimeScheduleInstance(application).timeScheduleDatabaseDao()
        val viewModelFactory = TimeScheduleViewModelFactory(dataSource, application)
        viewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory).get(TimeScheduleViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        viewModel.timeSchedules.observe(this, Observer { newTS ->
            binding.value1.text = newTS.get(0).start_at + " ~ " +newTS.get(0).end_at
            binding.value2.text = newTS.get(1).start_at + " ~ " +newTS.get(1).end_at
            binding.value3.text = newTS.get(2).start_at + " ~ " +newTS.get(2).end_at
            binding.value4.text = newTS.get(3).start_at + " ~ " +newTS.get(3).end_at
            binding.value5.text = newTS.get(4).start_at + " ~ " +newTS.get(4).end_at
        })

        val fragmentManager: FragmentManager? = getFragmentManager()
        binding.value1.setOnClickListener({
            if (fragmentManager != null) {
                val newDialog = TimeScheduleDialog(1)
                newDialog.show(fragmentManager, "TimeScheduleDialog_1")
            }
        })

        binding.value2.setOnClickListener({
            if (fragmentManager != null) {
                val newDialog = TimeScheduleDialog(2)
                newDialog.show(fragmentManager, "TimeScheduleDialog_2")
            }
        })

        binding.value3.setOnClickListener({
            if (fragmentManager != null) {
                val newDialog = TimeScheduleDialog(3)
                newDialog.show(fragmentManager, "TimeScheduleDialog_3")
            }
        })

        binding.value4.setOnClickListener({
            if (fragmentManager != null) {
                val newDialog = TimeScheduleDialog(4)
                newDialog.show(fragmentManager, "TimeScheduleDialog_4")
            }
        })

        binding.value5.setOnClickListener({
            val fragmentManager: FragmentManager? = getFragmentManager()
            if (fragmentManager != null) {
                val newDialog = TimeScheduleDialog(5)
                newDialog.show(fragmentManager, "TimeScheduleDialog_5")
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.menu_setting_item, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            view?.findNavController()?.navigate(R.id.action_timeScheduleFragment_to_settingFragment)
        }
        return super.onOptionsItemSelected(item)
    }
}
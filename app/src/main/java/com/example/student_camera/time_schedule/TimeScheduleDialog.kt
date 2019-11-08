package com.example.student_camera.time_schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.example.student_camera.R
import com.example.student_camera.database.AppDatabase
import com.example.student_camera.databinding.DialogFragmentTimeScheduleBinding

class TimeScheduleDialog(num: Int) : DialogFragment() {
    private lateinit var viewModel: TimeScheduleViewModel
    private lateinit var binding: DialogFragmentTimeScheduleBinding
    var num = num

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getTimeScheduleInstance(application).timeScheduleDatabaseDao()
        val viewModelFactory = TimeScheduleViewModelFactory(dataSource, application)
        viewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory).get(TimeScheduleViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_fragment_time_schedule,
            container,
            false
        )

        binding.title.text = num.toString() + "時間目"
        binding.startAtEdit.setText(viewModel.timeSchedules.value?.get(num - 1)?.start_at)
        binding.endAtEdit.setText(viewModel.timeSchedules.value?.get(num - 1)?.end_at)

        binding.saveButton.setOnClickListener({
            viewModel.updateTimeSchedule(num, binding.startAtEdit.text.toString(), binding.endAtEdit.text.toString())
            dismiss()
        })

        return binding.root
    }

//    override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
//        val dialog = super.onCreateDialog(savedInstanceState)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        return dialog
//    }
}
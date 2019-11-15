package com.example.student_camera.time_schedule

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.example.student_camera.R
import com.example.student_camera.database.AppDatabase
import com.example.student_camera.databinding.FragmentTimeScheduleDialogBinding

class TimeScheduleDialog(num: Int) : DialogFragment() {
    private lateinit var viewModel: TimeScheduleViewModel
    private lateinit var binding: FragmentTimeScheduleDialogBinding
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
            R.layout.fragment_time_schedule_dialog,
            container,
            false
        )

        binding.title.text = num.toString() + "時間目"
        binding.startAtEdit.setText(viewModel.timeSchedules.value?.get(num - 1)?.start_at)
        binding.endAtEdit.setText(viewModel.timeSchedules.value?.get(num - 1)?.end_at)

        binding.saveButton.setOnClickListener({
            val startAtEdit = binding.startAtEdit.text.toString()
            val endAtEdit = binding.endAtEdit.text.toString()

            val startIsValid = timeIsValid(startAtEdit)
            val endIsValid = timeIsValid(endAtEdit)

            if (startIsValid && endIsValid) {
//                Success
                viewModel.updateTimeSchedule(num, timeFormat(startAtEdit), timeFormat(endAtEdit))
                dismiss()
            } else {
//                Error
                binding.startErrorMsg.visibility = if (!startIsValid) {
                    View.VISIBLE
                } else {
                    View.INVISIBLE
                }

                binding.endErrorMsg.visibility = if (!endIsValid) {
                    View.VISIBLE
                } else {
                    View.INVISIBLE
                }
            }
        })

        return binding.root
    }
}

fun timeIsValid(timeStr: String): Boolean {
    try {
        val hourMinute = timeStr.split(":")
        val hourStr = hourMinute[0]
        val minStr = hourMinute[1]
        val hour = hourStr.toInt()
        val min = minStr.toInt()
        if (hour < 0 || 24 <= hour) {
            return false
        }
        if (min < 0 || 60 <= min) {
            return false
        }
        return true
    } catch (e: Exception) {
        return false
    }
}

fun timeFormat(timeStr: String): String {
    try {
        val timeList = timeStr.split(":")
        var hourStr = timeList[0]
        var minStr = timeList[1]
        val min = minStr.toInt()

        if (2 < hourStr.length) {
            hourStr = hourStr.substring(hourStr.length - 2, hourStr.length)
        }
        if (2 < minStr.length) {
            minStr = minStr.substring(minStr.length - 2, minStr.length)
        }
        if (min < 10 && minStr.length == 1) {
            minStr = "0" + minStr
        }

        return hourStr + ":" + minStr
    } catch (e: Exception) {
        Log.d("error", e.toString())
        return timeStr
    }
}
package com.example.student_camera.setting

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.student_camera.R
import com.example.student_camera.databinding.FragmentSettingBinding

class SettingFragment: Fragment() {
    private lateinit var binding: FragmentSettingBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)


        binding.opinion.setOnClickListener{ view: View ->
            view.findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToOpinionFragment())
        }

        binding.help.setOnClickListener{ view: View ->
            view.findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToHelpFragment())
        }

        binding.timeSchedule.setOnClickListener{ view: View ->
            view.findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToTimeScheduleFragment())
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.menu_setting, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.toString() =="Delete") {
            getActivity()!!.finish()
        }

        return super.onOptionsItemSelected(item)
    }
}
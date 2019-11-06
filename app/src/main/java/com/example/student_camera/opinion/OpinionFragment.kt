package com.example.student_camera.opinion

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.student_camera.R
import com.example.student_camera.databinding.FragmentOpinionBinding


class OpinionFragment: Fragment() {
    private lateinit var binding: FragmentOpinionBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, com.example.student_camera.R.layout.fragment_opinion, container, false)

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowHomeEnabled(true);
        (activity as AppCompatActivity).supportActionBar!!.setHomeButtonEnabled(true);

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.menu_setting_item, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
//            TODO いい感じにする
            view?.findNavController()?.navigate(R.id.action_opinionFragment_to_settingFragment)
        }
        return super.onOptionsItemSelected(item)
    }
}
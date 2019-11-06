package com.example.student_camera.help

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.student_camera.R
import com.example.student_camera.databinding.FragmentHelpBinding


class HelpFragment: Fragment() {
    private lateinit var binding: FragmentHelpBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_help, container, false)

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowHomeEnabled(true);
        (activity as AppCompatActivity).supportActionBar!!.setHomeButtonEnabled(true);

        binding.questionOne.setOnClickListener {
            binding.answerOne.visibility = switchVisibility(binding.answerOne.visibility)
        }

        binding.questionTwo.setOnClickListener {
            binding.answerTwo.visibility = switchVisibility(binding.answerTwo.visibility)
        }

        binding.questionThree.setOnClickListener {
            binding.answerThree.visibility = switchVisibility(binding.answerThree.visibility)
        }

        binding.questionFour.setOnClickListener {
            binding.answerFour.visibility = switchVisibility(binding.answerFour.visibility)
        }

        binding.questionFive.setOnClickListener {
            binding.answerFive.visibility = switchVisibility(binding.answerFive.visibility)
        }


        return binding.root
    }

    private fun switchVisibility (status: Int): Int{
        if (status == View.VISIBLE) {
            return View.GONE
        } else {
            return View.VISIBLE
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.menu_setting_item, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            view?.findNavController()?.navigate(R.id.action_helpFragment_to_settingFragment)
        }
        return super.onOptionsItemSelected(item)
    }
}
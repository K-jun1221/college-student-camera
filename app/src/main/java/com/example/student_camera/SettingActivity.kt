package com.example.student_camera

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity



class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        val inflater = menuInflater
//        inflater.inflate(R.menu.menu_setting, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//
//        Log.d("itemid", item.itemId.toString())
//
//        if (item.toString() == "Delete") {
//            finish()
//        }
//        return super.onOptionsItemSelected(item)
//    }
}
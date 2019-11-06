package com.example.student_camera.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo")
data class Photo(
    @PrimaryKey(autoGenerate = true)
    var photoId: Long = 0L,

    @ColumnInfo(name = "uri")
    var uri: String = "",

    @ColumnInfo(name = "created_at")
    var createdAt: String? = null
)

@Entity(tableName = "time_schedule")
data class TimeSchedule(
    @PrimaryKey(autoGenerate = true)
    var timeScheduleId: Long = 0L,

    @ColumnInfo(name = "num")
    var num: Int = 0,

    @ColumnInfo(name = "start_at")
    var start_at: String = "",

    @ColumnInfo(name = "end_at")
    var end_at: String = ""
)
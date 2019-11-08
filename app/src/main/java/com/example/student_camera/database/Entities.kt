package com.example.student_camera.database

import androidx.room.*
import java.util.*



@Entity(tableName = "photo")
@TypeConverters(Converters::class)
data class Photo(
    @PrimaryKey(autoGenerate = true)
    var photoId: Long = 0L,

    @ColumnInfo(name = "uri")
    var uri: String = "",

    @ColumnInfo(name = "day_cell_num")
    var dayCellTNum: Int = 0,

    @ColumnInfo(name = "time_cell_num")
    var timeCellNum: Int = 0,

    @ColumnInfo(name = "created_at")
    var createdAt: Date? = null
)

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}

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
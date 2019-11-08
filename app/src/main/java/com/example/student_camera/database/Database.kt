package com.example.student_camera.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Photo::class, TimeSchedule::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun photoDatabaseDao(): PhotoDatabaseDao
    abstract fun timeScheduleDatabaseDao(): TimeScheduleDatabaseDao

    companion object {

        @Volatile
        private var PHOTO_INSTANCE: AppDatabase? = null
        private var TIME_SCHEDULE_INSTANCE: AppDatabase? = null

        fun getPhotoInstance(context: Context): AppDatabase {

            synchronized(this) {
                var instance = PHOTO_INSTANCE

                if (instance == null) {

                    // migration的なもの
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "photo"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    PHOTO_INSTANCE = instance
                }
                return instance
            }
        }

        fun getTimeScheduleInstance(context: Context): AppDatabase {

            synchronized(this) {
                var instance = TIME_SCHEDULE_INSTANCE

                if (instance == null) {

                    // migration的なもの
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "time_schedule"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    TIME_SCHEDULE_INSTANCE = instance
                }
                return instance
            }
        }
    }
}
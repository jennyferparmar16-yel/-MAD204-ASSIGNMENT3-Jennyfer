/*
name : Jennyfer Parmar
Student Id: A00201240
date 12/12/25

Description:
This file defines the Room database.
It provides access to DAO objects.
*/
package com.example.project_3_jennyferparmar

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Room database configuration
@Database(entities = [Notes::class, Favorite::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    // Provides access to DAO
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        // Returns a single database instance
        fun getInstance(ctx: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    ctx.applicationContext,
                    AppDatabase::class.java,
                    "notes_db"
                )
                    .allowMainThreadQueries() // Allowed for simplicity
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}

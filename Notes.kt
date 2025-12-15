/*
name : Jennyfer Parmar
Student Id: A00201240
date 12/12/25

Description:
This file defines the data model for notes.
It represents the notes table in the Room database.
*/
package com.example.project_3_jennyferparmar

import androidx.room.Entity
import androidx.room.PrimaryKey

// Room entity representing a single note
@Entity(tableName = "notes")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,          // Unique ID generated automatically

    val title: String,         // Title of the note
    val content: String,       // Main content of the note
    val mediaUri: String? = null // Optional image or video path
)

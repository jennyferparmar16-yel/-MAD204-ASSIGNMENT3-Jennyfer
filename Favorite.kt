/*
name : Jennyfer Parmar
Student Id: A00201240
date 12/12/25

Description:
This file stores favorite notes.
It links a note ID to the favorites table.
*/
package com.example.project_3_jennyferparmar

import androidx.room.Entity
import androidx.room.PrimaryKey

// Room entity for favorite notes
@Entity(tableName = "favorites")
data class Favorite(
    @PrimaryKey
    val noteId: Long // References the ID of a note marked as favorite
)

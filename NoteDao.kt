/*
name : Jennyfer Parmar
Student Id: A00201240
date 12/12/25

Description:
This interface defines all database operations.
Room generates the implementation automatically.
*/
package com.example.project_3_jennyferparmar

import androidx.room.*

// DAO interface for database queries
@Dao
interface NoteDao {

    // Inserts a new note into the database
    @Insert
    fun insertNote(note: Notes): Long

    // Updates an existing note
    @Update
    fun updateNote(note: Notes)

    // Deletes a note
    @Delete
    fun deleteNote(note: Notes)

    // Retrieves all notes sorted by newest first
    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes(): List<Notes>

    // Retrieves a single note by ID
    @Query("SELECT * FROM notes WHERE id = :id LIMIT 1")
    fun getNoteById(id: Long): Notes?

    // Adds a note to the favorites table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(favorite: Favorite)

    // Removes a note from favorites
    @Delete
    fun removeFavorite(favorite: Favorite)

    // Checks whether a note is marked as favorite
    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE noteId = :noteId)")
    fun isFavorite(noteId: Long): Boolean

    // Retrieves all favorite notes
    @Query("""
        SELECT n.* FROM notes n
        INNER JOIN favorites f ON n.id = f.noteId
        ORDER BY n.id DESC
    """)
    fun getFavoriteNotes(): List<Notes>
}

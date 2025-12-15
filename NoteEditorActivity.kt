/*
name : Jennyfer Parmar
Student Id: A00201240
date 12/12/25

Description:
This activity is used to add a new note or edit an existing note.
*/
package com.example.project_3_jennyferparmar

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NoteEditorActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private var noteId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enables edge-to-edge content
        enableEdgeToEdge()

        // Loads the editor layout
        setContentView(R.layout.activity_note_editor)

        // Applies padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Database instance
        db = AppDatabase.getInstance(this)

        // Input fields
        val etTitle = findViewById<EditText>(R.id.etTitle)
        val etContent = findViewById<EditText>(R.id.etContent)

        // Read noteId if editing
        noteId = intent.getLongExtra("noteId", -1)

        // If editing, load the note into fields
        if (noteId != -1L) {
            val note = db.noteDao().getNoteById(noteId)
            etTitle.setText(note?.title)
            etContent.setText(note?.content)
        }

        // Save note when button clicked
        findViewById<View>(R.id.btnsaveNote).setOnClickListener {
            val title = etTitle.text.toString().trim()
            val content = etContent.text.toString().trim()

            if (title.isBlank()) {
                etTitle.error = "Title required"
                return@setOnClickListener
            }

            if (noteId == -1L) {
                // Insert a new note
                db.noteDao().insertNote(Notes(title = title, content = content))
            } else {
                // Update existing note
                db.noteDao().updateNote(Notes(id = noteId, title = title, content = content))
            }

            finish() // Close the editor and return
        }
    }
}

/*
name : Jennyfer Parmar
Student Id: A00201240
date 12/12/25

Description:
This is the main screen of the app. It shows Favorites and All Notes using RecyclerViews.
It also handles Add Note, Settings navigation, and note actions (delete/favorite).
*/
package com.example.project_3_jennyferparmar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), NoteAdapter.Listener {

    private lateinit var db: AppDatabase
    private val allNotes = mutableListOf<Notes>()
    private val favNotes = mutableListOf<Notes>()
    private lateinit var allAdapter: NoteAdapter
    private lateinit var favAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enables edge-to-edge content (draws behind system bars)
        enableEdgeToEdge()

        // Loads the XML layout for this activity
        setContentView(R.layout.activity_main)

        // Applies padding so content is not hidden under status/navigation bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get database instance
        db = AppDatabase.getInstance(this)

        // RecyclerView references
        val rvAll = findViewById<RecyclerView>(R.id.rvAllNotes)
        val rvFav = findViewById<RecyclerView>(R.id.rvFavorites)

        // Setup layouts for lists
        rvAll.layoutManager = LinearLayoutManager(this)
        rvFav.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        // Setup adapters
        allAdapter = NoteAdapter(allNotes, db, this)
        favAdapter = NoteAdapter(favNotes, db, this)

        rvAll.adapter = allAdapter
        rvFav.adapter = favAdapter

        // Add Note screen
        findViewById<View>(R.id.btnAddNote).setOnClickListener {
            startActivity(Intent(this, NoteEditorActivity::class.java))
        }

        // Settings screen
        findViewById<View>(R.id.btnSettings).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        refreshLists() // Reload notes when returning to main screen
    }

    private fun refreshLists() {
        allNotes.clear()
        favNotes.clear()

        // Load notes from database
        allNotes.addAll(db.noteDao().getAllNotes())
        favNotes.addAll(db.noteDao().getFavoriteNotes())

        // Refresh RecyclerViews
        allAdapter.notifyDataSetChanged()
        favAdapter.notifyDataSetChanged()
    }

    override fun onNoteClick(note: Notes) {
        // Open editor with note id for editing
        val i = Intent(this, NoteEditorActivity::class.java)
        i.putExtra("noteId", note.id)
        startActivity(i)
    }

    override fun onDelete(note: Notes) {
        // Delete note and remove from favorites if needed
        db.noteDao().deleteNote(note)
        db.noteDao().removeFavorite(Favorite(note.id))
        refreshLists()
    }

    override fun onToggleFavorite(note: Notes) {
        // Toggle favorite state
        if (db.noteDao().isFavorite(note.id)) {
            db.noteDao().removeFavorite(Favorite(note.id))
        } else {
            db.noteDao().addFavorite(Favorite(note.id))
        }
        refreshLists()
    }
}

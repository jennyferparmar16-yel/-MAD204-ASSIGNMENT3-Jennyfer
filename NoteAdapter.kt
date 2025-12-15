/*
name : Jennyfer Parmar
Student Id: A00201240
date 12/12/25

Description:
This adapter connects the notes data to the RecyclerView.
It handles click, delete, and favorite actions.
*/
package com.example.project_3_jennyferparmar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adapter class for RecyclerView
class NoteAdapter(
    private val items: MutableList<Notes>, // List of notes
    private val db: AppDatabase,            // Database reference
    private val listener: Listener          // Event callbacks
) : RecyclerView.Adapter<NoteAdapter.VH>() {

    // Interface for click actions
    interface Listener {
        fun onNoteClick(note: Notes)
        fun onDelete(note: Notes)
        fun onToggleFavorite(note: Notes)
    }

    // ViewHolder for row layout
    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val tvTitle: TextView = v.findViewById(R.id.tvTitle)
        val tvContent: TextView = v.findViewById(R.id.tvContent)
        val btnDelete: ImageButton = v.findViewById(R.id.btnDelete)
        val btnFav: ImageButton = v.findViewById(R.id.btnFav)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_note, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(h: VH, position: Int) {
        val note = items[position]

        // Bind note data to UI
        h.tvTitle.text = note.title
        h.tvContent.text = note.content

        // Update favorite icon
        val isFav = db.noteDao().isFavorite(note.id)
        h.btnFav.setImageResource(
            if (isFav) android.R.drawable.btn_star_big_on
            else android.R.drawable.btn_star_big_off
        )

        // Handle user actions
        h.itemView.setOnClickListener { listener.onNoteClick(note) }
        h.btnDelete.setOnClickListener { listener.onDelete(note) }
        h.btnFav.setOnClickListener { listener.onToggleFavorite(note) }
    }

    override fun getItemCount(): Int = items.size
}

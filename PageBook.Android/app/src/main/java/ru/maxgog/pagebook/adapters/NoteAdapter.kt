package ru.maxgog.pagebook.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import ru.maxgog.pagebook.R
import ru.maxgog.pagebook.models.NoteModel

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var notes = emptyList<NoteModel>()

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notes[position]
        holder.titleTextView.text = currentNote.title
        holder.contentTextView.text = currentNote.content
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun setNotes(notes: List<NoteModel>) {
        this.notes = notes
        notifyDataSetChanged()
    }
}
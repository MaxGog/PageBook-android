package ru.maxgog.pagebook

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

import ru.maxgog.pagebook.adapters.NoteAdapter
import ru.maxgog.pagebook.databinding.ActivityMainBinding
import ru.maxgog.pagebook.models.NoteModel
import ru.maxgog.pagebook.viewmodels.NoteViewModel

class MainActivity : AppCompatActivity() {

    private val noteViewModel: NoteViewModel by viewModels()
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = NoteAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        noteViewModel.allNotes.observe(this, { notes ->
            notes?.let { adapter.setNotes(it) }
        })

        binding.fab.setOnClickListener {
            val note = NoteModel(title = "New Note", content = "Note content")
            noteViewModel.insert(note)
        }
    }
}
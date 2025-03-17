package ru.maxgog.pagebook.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import ru.maxgog.pagebook.models.NoteModel
import ru.maxgog.pagebook.storage.NoteRepository
import ru.maxgog.pagebook.storage.NoteRoomDatabase

class MainViewModel (application: Application) : ViewModel() {
    val noteList: LiveData<List<NoteModel>>
    private val repository: NoteRepository

    init {
        val noteDb = NoteRoomDatabase.getInstance(application)
        val noteDao = noteDb.noteDao()
        repository = NoteRepository(noteDao)
        noteList = repository.noteList
    }

}
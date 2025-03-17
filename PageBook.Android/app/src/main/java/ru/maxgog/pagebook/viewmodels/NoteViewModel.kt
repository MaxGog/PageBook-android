package ru.maxgog.pagebook.viewmodels

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import ru.maxgog.pagebook.models.NoteModel
import ru.maxgog.pagebook.storage.NoteRepository
import ru.maxgog.pagebook.storage.NoteRoomDatabase
import java.time.LocalDate

class NoteViewModel(application: Application) : ViewModel() {
    val noteList: LiveData<List<NoteModel>>
    private val repository: NoteRepository
    var title = String
    var content = String
    //var atCreated by mutableStateOf()

    init {
        val noteDb = NoteRoomDatabase.getInstance(application)
        val noteDao = noteDb.noteDao()
        repository = NoteRepository(noteDao)
        noteList = repository.noteList
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun addNote() {
        repository.addNote(NoteModel(title = title.toString(), content = content.toString(), atCreated = LocalDate.now()))
    }
    fun deleteNote(id: Int) {
        repository.deleteNote(id)
    }
}
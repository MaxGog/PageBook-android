package ru.maxgog.pagebook.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Entity
import java.time.LocalDate

@Entity(tableName = "notes")
data class NoteModel(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "NoteId") val id: Int = 0,
    @ColumnInfo(name = "Title") val title: String = "",
    @ColumnInfo(name = "Content") val content: String = "",
    @ColumnInfo(name = "AtCreated") val atCreated: LocalDate?
    )
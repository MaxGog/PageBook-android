package ru.maxgog.pagebook.models

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Entity
import java.sql.Date

@Entity(tableName = "notes")
data class NoteModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "NoteId") val id: Int,
    @ColumnInfo(name = "Title") val title: String,
    @ColumnInfo(name = "Content") val content: String,
    @ColumnInfo(name = "AtCreated") val atCreated: Date
    )
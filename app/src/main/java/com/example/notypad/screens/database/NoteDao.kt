package com.example.notypad.screens.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notypad.screens.models.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(delete: Note)

    @Query("SELECT * FROM NotesTable ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("UPDATE NotesTable Set title = :title, note = :note WHERE id = :id")
    suspend fun update(id: Int?, title: String?, note: String?)
}
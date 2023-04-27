package com.example.noteapp

import android.content.Context
import androidx.room.*


//TODO:  1- Convert Class to Entity
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val ID: Int,
    @ColumnInfo(name = "Title") val Title: String,
    @ColumnInfo(name = "Description")  val Description: String )

//TODO: 2- Define Dao
@Dao
interface NoteDao{
    @Insert
    fun insert(vararg note: Note)
    @Delete
    fun delete(note: Note)
    @Update
    fun update(note: Note)

    @Query("Select * From Note Where Title Like :title")
    fun loadByTiltle(title :String):List<Note>
}

// TODO: 3- create database
@Database(entities = arrayOf(Note::class), version = 1)
abstract class NotesDatabase:RoomDatabase(){
    abstract fun NoteDao():NoteDao
}



//TODO: 4- Create database instance
class DBManager{
    @Volatile
    private var instance:NotesDatabase?=null
    fun getDatabase(context: Context):NotesDatabase?{
        if(instance==null){
            synchronized(NotesDatabase::class.java){
                if (instance==null){
                    instance= Room.databaseBuilder(context.applicationContext,
                        NotesDatabase::class.java!!,"MyNotesDB")
                        .allowMainThreadQueries()
                        .build()
                }
            }
        }
        return instance
    }
}

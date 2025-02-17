package lk.ac.kln.nextup

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// a helper class to manage the SQLite database for storing notes
// provides functionality for creating, upgrading, and managing the database
class NotesDatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        // database name and version constants
        private const val DATABASE_NAME = "notesapp.db"
        private const val DATABASE_VERSION = 1

        // table and column names constants
        private const val TABLE_NAME ="allnotes"
        private const val COLUMN_ID ="id"
        private const val COLUMN_TITLE = "tittle"
        private const val COLUMN_CONTENT = "content"

    }


    // called when the database is created for the first time
    // reates the notes table with columns for ID, title, and content
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT)"
        db?.execSQL(createTableQuery)
    }

    //  called when the database needs to be upgraded
    //  drops the existing notes table and creates a new one

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    // inserts a new note into the database
    fun insertNote(note: Note){
        val db = writableDatabase
        // column name
        val values = ContentValues().apply{
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)
        }
        // insert the values into the table
        db.insert(TABLE_NAME, null,values)
        db.close()
    }

    // retrieves all notes from the database
    fun getAllNotes(): List<Note>{
        val notesList = mutableListOf<Note>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        // iterate through the result set and add each note to the list
        while(cursor.moveToNext()){
            val id = cursor .getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor .getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor .getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

            val note = Note(id, title, content)
            notesList.add(note)
        }
        cursor.close()
        db.close()
        return notesList
    }

    //update an existing note in the database
    fun updateNote(note: Note){
        val db = writableDatabase
        val values = ContentValues().apply{
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(note.id.toString())
        // update the database with the new values
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    // retrieves a single note by its ID
    fun getNoteByID(noteId: Int): Note{
        val db = readableDatabase
        val query ="SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor .getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor .getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content = cursor .getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close()
        db.close()
        return Note(id, title, content)
    }

    // delete a note from the database by its ID
    fun deleteNote(noteId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(noteId.toString())
        // delete the note with the specified ID
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }
}
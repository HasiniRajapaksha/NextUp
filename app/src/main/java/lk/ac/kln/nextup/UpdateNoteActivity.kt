package lk.ac.kln.nextup

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import lk.ac.kln.nextup.databinding.ActivityUpdateNoteBinding

// UpdateNoteActivity allows the user to update an existing note
// it fetches the note data, displays it in editable fields, and saves changes

class UpdateNoteActivity : AppCompatActivity() {

    // view binding to access the layout elements
    private lateinit var binding: ActivityUpdateNoteBinding
    // database helper to interact with the notes database
    private lateinit var db: NotesDatabaseHelper
    // variable to store the ID of the note being updated
    private var noteId: Int = -1


    // onCreate sets up the activity, initializes the database,and loads the note data for editing
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // inflate the layout using ViewBinding and set it as the content view
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)


        // get the note ID passed from the previous activity
        noteId = intent.getIntExtra("note_id", -1)
       // if the note ID is invalid, finish the activity
        if(noteId == -1) {
            finish()
            return
        }

        // fetch the note data from the database using the ID
        val note = db.getNoteByID(noteId)
        // set the note title and content in the corresponding EditText fields
        binding.updateTittleEditText.setText(note.title)
        binding.updateContentEditText.setText(note.content)


        // set up the save button to update the note when clicked
        binding.updateSaveButton.setOnClickListener{
            val newTitle = binding.updateTittleEditText.text.toString()
            val newContent = binding.updateContentEditText.text.toString()
            // create a new Note object with the updated data
            val updateNote = Note(noteId, newTitle, newContent)
            db.updateNote(updateNote)
            finish()
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()








        }
    }
}
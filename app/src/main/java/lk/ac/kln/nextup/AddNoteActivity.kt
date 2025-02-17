package lk.ac.kln.nextup

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import lk.ac.kln.nextup.databinding.ActivityAddNoteBinding



// addNoteActivity is responsible for allowing the user to add a new note
// this activity interacts with the NotesDatabaseHelper to store the note data

class AddNoteActivity : AppCompatActivity() {

    // viewBinding to access the layout elements
    private lateinit var binding: ActivityAddNoteBinding
    // database helper for handling note-related operations
    private lateinit var db: NotesDatabaseHelper



    // onCreate initializes the activity, sets up the view binding
    //and defines the behavior for the save button click

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // inflate the layout using viewbinding and set it as the content view
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // initialize the notesdatabasehelper for interacting with the database
        db = NotesDatabaseHelper(this)

        // only insert data if save button is clicked
        binding.saveButton.setOnClickListener {
            val title = binding.tittleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            // create a new Note object with the title and content
            val note = Note(0, title, content)
            // insert note data
            db.insertNote(note)
            finish()
            // show a toast message confirming that the note has been saved
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()


        }

    }


}
package lk.ac.kln.nextup

import android.content.Intent
import android.os.Bundle
import android.provider.Telephony.Mms.Intents
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import lk.ac.kln.nextup.databinding.ActivityMainBinding


// MainActivity is the main screen of the app that shows a list of saved notes
// it initializes the RecyclerView to display the notes and allows users to add a new note

class MainActivity : AppCompatActivity() {

    // view binding to access layout elements
    private lateinit var binding: ActivityMainBinding
    // database helper for managing notes
    private lateinit var db: NotesDatabaseHelper
    // adapter to display notes in the RecyclerView
    private lateinit var notesAdapter: NotesAdapter



    // onCreate sets up the layout, initializes database, and configures the RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // inflate the layout using view binding and set it as the content view
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // initialize the database helper for getting all notes
        db = NotesDatabaseHelper(this)

        // initialize the adapter with all notes from the database
        notesAdapter = NotesAdapter(db.getAllNotes(),this)

        // set up the RecyclerView with a LinearLayoutManager and the adapter
        binding.notesRecyclerView.layoutManager =LinearLayoutManager(this)
        binding.notesRecyclerView.adapter = notesAdapter

        // set up the floating add button to open the AddNoteActivity when clicked
        binding.addButton.setOnClickListener{
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }

    }

    //onResume refreshes the data in the RecyclerView when returning to this activity
    override fun onResume(){
        super.onResume()
        // refresh the notes data by getting the latest notes from the database
        notesAdapter.refreshData(db.getAllNotes())
    }

}
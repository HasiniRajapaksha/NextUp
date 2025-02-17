package lk.ac.kln.nextup

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

// adapter class for displaying notes in a RecyclerView
// handles the creation, binding, and deletion of note items
class NotesAdapter(private var notes: List<Note>, context: Context) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val db: NotesDatabaseHelper = NotesDatabaseHelper(context)

    // ViewHolder class to represent a single note item in the RecyclerView
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    // creates a new ViewHolder instance for a note item
    // inflates the layout for a single note item and returns the ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent,false)
        return NoteViewHolder(view)
    }

    // return the number of items in the notes list
    override fun getItemCount(): Int = notes.size


    // binds data to the individual note item views (title, content)
    // sets up click listeners for the update and delete buttons
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content

        // set up click listener for updating a note
        holder.updateButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply{
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        // set up click listener for deleting a note
        holder.deleteButton.setOnClickListener{
            db.deleteNote(note.id)
            refreshData(db.getAllNotes())
            Toast.makeText(holder.itemView.context, "Note Deleted", Toast.LENGTH_SHORT).show()
        }
    }


    // referesh the data displayed in the RecyclerView by updating the notes list
    fun refreshData(newNotes: List<Note>){
        notes = newNotes
        notifyDataSetChanged()
    }
}
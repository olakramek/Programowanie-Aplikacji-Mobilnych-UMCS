package com.example.testaplikacja

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class NotesFragment : Fragment() {
    private lateinit var btnAddNote: Button
    private lateinit var btnMyNotes: Button
    private lateinit var btnSharedNotes: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var textViewNotes: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var email: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnAddNote = view.findViewById(R.id.btnAddNote)
        btnMyNotes = view.findViewById(R.id.btnMyNotes)
        btnSharedNotes = view.findViewById(R.id.btnSharedNotes)
        recyclerView = view.findViewById(R.id.recyclerViewNotes)
        textViewNotes = view.findViewById(R.id.textViewNotes)

        // Initialize RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        btnAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_notesFragment_to_addNoteFragment)
        }

        btnMyNotes.setOnClickListener {
            displayNotes("myNotes")
        }

        btnSharedNotes.setOnClickListener {
            displayNotes("sharedNotes")
        }
    }

    private fun displayNotes(noteType: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        if (user != null) {
            email = sanitizeEmail(user.email ?: "")
        }
        if (userId != null) {
            val databaseReference = when (noteType) {
                "myNotes" -> FirebaseDatabase.getInstance("https://testaplikacja-dd4c3-default-rtdb.europe-west1.firebasedatabase.app")
                    .getReference("notes/$userId")
                "sharedNotes" -> FirebaseDatabase.getInstance("https://testaplikacja-dd4c3-default-rtdb.europe-west1.firebasedatabase.app")
                    .getReference("sharedNotes/$email")
                else -> null
            }

            databaseReference?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val notesList = mutableListOf<Note>()

                    for (noteSnapshot in snapshot.children) {
                        val note = noteSnapshot.getValue(Note::class.java)
                        if (note != null) {
                            notesList.add(note)
                        }
                    }

                    if (noteType == "myNotes") {
                        recyclerView.visibility = View.VISIBLE
                        textViewNotes.visibility = View.GONE
                        recyclerView.adapter = MyNotesAdapter(notesList, object : MyNotesAdapter.NoteClickListener {
                            override fun onEditClick(note: Note) {
                                // Handle edit click
                                handleNoteClicks(note)
                            }

                            override fun onDeleteClick(note: Note) {
                                // Handle delete click
                                handleNoteClicks(note)
                            }
                        })
                    } else if (noteType == "sharedNotes") {
                        recyclerView.visibility = View.VISIBLE
                        textViewNotes.visibility = View.GONE
                        recyclerView.adapter = SharedNotesAdapter(notesList)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("NotesFragment", "Error getting notes", error.toException())
                    Toast.makeText(requireContext(), "Wystąpił błąd", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun handleNoteClicks(note: Note) {


        val noteTitle = note.title

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null && noteTitle != null) {
            val databaseReference = FirebaseDatabase.getInstance("https://testaplikacja-dd4c3-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("notes/$userId")

            val query = databaseReference.orderByChild("title").equalTo(noteTitle)

            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (noteSnapshot in snapshot.children) {
                        noteSnapshot.ref.removeValue()
                            .addOnSuccessListener {
                                Toast.makeText(requireContext(), "Notatka została usunięta", Toast.LENGTH_SHORT).show()

                                displayNotes("myNotes")
                            }
                            .addOnFailureListener {
                                Toast.makeText(requireContext(), "Nie udało się usunąć notatki", Toast.LENGTH_SHORT).show()
                            }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Wystąpił błąd", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }



    private fun sanitizeEmail(email: String): String {
        return email.replace(".", "_dot_")
            .replace("@", "_at_")
            .replace("#", "_hash_")
            .replace("$", "_dollar_")
            .replace("[", "_leftBracket_")
            .replace("]", "_rightBracket_")
    }
}

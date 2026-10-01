package com.example.testaplikacja

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.widget.Toast
class AddNoteFragment : Fragment() {

    private lateinit var etNoteTitle: EditText
    private lateinit var etNoteContent: EditText
    private lateinit var etShareEmail: EditText
    private lateinit var btnSaveNote: Button
    private lateinit var btnShareNote: Button

    private lateinit var database: DatabaseReference
    private lateinit var sharedDatabase: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_note, container, false)

        etNoteTitle = view.findViewById(R.id.editTextNoteTitle)
        etNoteContent = view.findViewById(R.id.editTextNoteContent)
        etShareEmail = view.findViewById(R.id.editTextEmail)
        btnSaveNote = view.findViewById(R.id.btnSaveNote)
        btnShareNote = view.findViewById(R.id.btnShareNote)

        auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid


        database = FirebaseDatabase.getInstance("https://testaplikacja-dd4c3-default-rtdb.europe-west1.firebasedatabase.app").getReference("notes/$userId")
        sharedDatabase = FirebaseDatabase.getInstance("https://testaplikacja-dd4c3-default-rtdb.europe-west1.firebasedatabase.app").getReference("sharedNotes")

        btnSaveNote.setOnClickListener {
            val title = etNoteTitle.text.toString().trim()
            val content = etNoteContent.text.toString().trim()

            if (title.isNotEmpty()) {
                val note = Note(title, content)
                database.child(title).setValue(note)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            showToast("Udało się dodać notatkę.")



                        } else {
                            showToast("Nie udało się zapisać notatki.")
                        }
                    }
            } else {
                showToast("Proszę wprowadzić tytuł.")
            }
        }

        btnShareNote.setOnClickListener {
            val title = etNoteTitle.text.toString().trim()
            val shareEmail = etShareEmail.text.toString().trim()

            if (title.isNotEmpty() && shareEmail.isNotEmpty()) {

                val sanitizedEmail = shareEmail.replace(".", "_dot_")
                    .replace("@", "_at_")
                    .replace("#", "_hash_")
                    .replace("$", "_dollar_")
                    .replace("[", "_leftBracket_")
                    .replace("]", "_rightBracket_")

                val note = Note(title, etNoteContent.text.toString().trim())
                sharedDatabase.child(sanitizedEmail).child(title).setValue(note)
                database.child(title).setValue(note)
                etNoteTitle.text.clear()
                etNoteContent.text.clear()
                etShareEmail.text.clear()
                showToast("Notatka udostępniona dla $shareEmail")
            } else {
                showToast("Proszę wporowadzić poprawny tytuł oraz Email.")
            }

        }

        return view
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}

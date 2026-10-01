package com.example.testaplikacja

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainFragment : Fragment() {
    private val client = OkHttpClient()
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchNameday(view)

        val btnNotes = view.findViewById<Button>(R.id.btnNotes)
        btnNotes.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_notesFragment)
        }

        val btnReminder = view.findViewById<Button>(R.id.btnSetReminder)
        btnReminder.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_reminderFragment)
        }

        val btnList = view.findViewById<Button>(R.id.btnLista)
        btnList.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_ListFragment)
        }



        val btnLogout = view.findViewById<Button>(R.id.btnWyloguj)
        btnLogout.setOnClickListener {
            logoutUser()
        }
    }

    private fun fetchNameday(view: View) {
        val request = Request.Builder()
            .url("https://nameday.abalin.net/api/V1/today?country=pl")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val nameday = response.body!!.string()
                    val jsonObject = JSONObject(nameday)
                    val namedayPL = jsonObject.getJSONObject("nameday").getString("pl")
                    val day = jsonObject.getInt("day")
                    val month = jsonObject.getInt("month")
                    val date = "$day/$month"

                    activity?.runOnUiThread {
                        val textView = view.findViewById<TextView>(R.id.namedayTextView)
                        textView.text = "Dzisiaj jest $date.\nImieniny obchodzą:\n$namedayPL"
                    }
                }
            }
        })
    }
    private fun logoutUser() {
        auth = FirebaseAuth.getInstance()
        auth.signOut()

        val intent = Intent(activity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        activity?.finish()
    }

}
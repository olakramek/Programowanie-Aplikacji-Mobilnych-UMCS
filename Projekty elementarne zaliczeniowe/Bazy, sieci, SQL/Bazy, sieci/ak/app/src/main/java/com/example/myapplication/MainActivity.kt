package com.example.myapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
class MainActivity : AppCompatActivity() {

    private val authors = mutableListOf<String>()
    private val books = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        authors.add("John Doe")
        authors.add("Jane Smith")
        books.add("The Great Gatsby")
        books.add("To Kill a Mockingbird")


        val authorTextView = findViewById<AutoCompleteTextView>(R.id.author)
        val bookTextView = findViewById<AutoCompleteTextView>(R.id.book)

        val authorAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, authors)
        val bookAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, books)

        authorTextView.setAdapter(authorAdapter)
        bookTextView.setAdapter(bookAdapter)

        val addButton = findViewById<Button>(R.id.button)
        addButton.setOnClickListener {

            val author = authorTextView.text.toString()
            val book = bookTextView.text.toString()

            authors.add(author)
            books.add(book)

            authorAdapter.notifyDataSetChanged()
            bookAdapter.notifyDataSetChanged()

            authorTextView.setText("")
            bookTextView.setText("")
        }
    }
}


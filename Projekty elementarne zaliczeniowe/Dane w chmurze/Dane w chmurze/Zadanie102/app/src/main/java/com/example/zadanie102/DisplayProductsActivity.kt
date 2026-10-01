package com.example.zadanie102



import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DisplayProductsActivity : AppCompatActivity() {

    private lateinit var textViewProducts: TextView

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_products)

        textViewProducts = findViewById(R.id.textViewProducts)


        database = FirebaseDatabase.getInstance("https://zadanie10-2-default-rtdb.europe-west1.firebasedatabase.app").getReference("products")

        displayProducts()
    }

    private fun displayProducts() {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val productList = mutableListOf<String>()

                for (productSnapshot in snapshot.children) {
                    val product = productSnapshot.getValue(Product::class.java)
                    if (product != null) {
                        productList.add(product.name)
                    }
                }

                if (productList.isEmpty()) {
                    textViewProducts.text = "List of products is empty"
                } else {
                    val productsText = productList.joinToString("\n")
                    textViewProducts.text = productsText
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("DisplayProductsActivity", "Error getting products", error.toException())
                Toast.makeText(this@DisplayProductsActivity, "Error getting products", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

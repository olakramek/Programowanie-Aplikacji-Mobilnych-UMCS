package com.example.zadanie102

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddProductActivity : AppCompatActivity() {

    private lateinit var etProductName: EditText
    private lateinit var btnAddProduct: Button

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        etProductName = findViewById(R.id.etProductName)
        btnAddProduct = findViewById(R.id.btnAddProduct)


        database = FirebaseDatabase.getInstance("https://zadanie10-2-default-rtdb.europe-west1.firebasedatabase.app").getReference("products")

        btnAddProduct.setOnClickListener {
            addProduct()
            Log.e("AddProductActivity", "proba dodania")
        }
    }

    private fun addProduct() {
        val productName = etProductName.text.toString()

        if (productName.isNotEmpty()) {

            val productId = database.push().key!!

            val product = Product(productName)

            database.child(productId).setValue(product)
                .addOnCompleteListener {
                    Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show()
                    Log.e("AddProductActivity", "DODANO")
                    etProductName.text.clear()
                }
                .addOnFailureListener { err ->
                    Log.e("AddProductActivity", "Error adding product: ${err.message}")
                    Toast.makeText(this, "Error adding product", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Please enter a product name", Toast.LENGTH_SHORT).show()
        }
    }
}

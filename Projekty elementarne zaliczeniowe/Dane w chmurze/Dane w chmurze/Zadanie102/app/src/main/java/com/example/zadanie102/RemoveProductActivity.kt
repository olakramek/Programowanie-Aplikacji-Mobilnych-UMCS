package com.example.zadanie102

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RemoveProductActivity : AppCompatActivity() {

    private lateinit var etProductNameToRemove: EditText
    private lateinit var btnRemoveProduct: Button

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remove_product)

        etProductNameToRemove = findViewById(R.id.etProductNameToRemove)
        btnRemoveProduct = findViewById(R.id.btnRemoveProduct)


        database = FirebaseDatabase.getInstance("https://zadanie10-2-default-rtdb.europe-west1.firebasedatabase.app").getReference("products")

        btnRemoveProduct.setOnClickListener {
            removeProduct()
        }
    }

    private fun removeProduct() {
        val productName = etProductNameToRemove.text.toString().trim()

        if (productName.isNotEmpty()) {
            database.orderByChild("name").equalTo(productName).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (productSnapshot in snapshot.children) {
                        productSnapshot.ref.removeValue()
                        Log.d("RemoveProductActivity", "Removed product: $productName")
                        Toast.makeText(this@RemoveProductActivity, "Product removed successfully", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("RemoveProductActivity", "Error removing product", error.toException())
                    Toast.makeText(this@RemoveProductActivity, "Error removing product", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            etProductNameToRemove.error = "Please enter product name to remove"
        }
    }
}

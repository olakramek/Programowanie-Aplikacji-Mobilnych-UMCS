package com.example.zadanie102

import android.content.Intent
import android.os.Bundle

import android.widget.Button

import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var btnAddProduct: Button
    private lateinit var btnRemoveProduct: Button
    private lateinit var btnDisplayProducts: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAddProduct = findViewById(R.id.btnAddProduct)
        btnRemoveProduct = findViewById(R.id.btnRemoveProduct)
        btnDisplayProducts = findViewById(R.id.btnDisplayProducts)

        btnAddProduct.setOnClickListener {
            startActivity(Intent(this, AddProductActivity::class.java))
        }

        btnRemoveProduct.setOnClickListener {
            startActivity(Intent(this, RemoveProductActivity::class.java))
        }
//
        btnDisplayProducts.setOnClickListener {
            startActivity(Intent(this, DisplayProductsActivity::class.java))
        }
    }
}

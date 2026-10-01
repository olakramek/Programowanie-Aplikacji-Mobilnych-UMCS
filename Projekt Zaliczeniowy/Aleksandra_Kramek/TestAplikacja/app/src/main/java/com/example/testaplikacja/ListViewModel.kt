package com.example.testaplikacja

import androidx.lifecycle.ViewModel

class ListViewModel : ViewModel() {
    val items = mutableListOf<String>()
}

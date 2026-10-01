package com.example.testaplikacja

data class Note(
    val title: String = "",
    val description: String = ""
) {
    // Add a no-argument constructor
    constructor() : this("", "")
}


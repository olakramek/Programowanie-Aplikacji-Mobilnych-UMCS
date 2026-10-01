package com.example.testaplikacja

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class ListFragment : Fragment() {

    private lateinit var etItem: EditText
    private lateinit var btnAddItem: Button
    private lateinit var listView: ListView
    private val listViewModel: ListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_list, container, false)

        etItem = rootView.findViewById(R.id.etItem)
        btnAddItem = rootView.findViewById(R.id.btnAddItem)
        listView = rootView.findViewById(R.id.listView)

        btnAddItem.setOnClickListener {
            addItem()
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateList()

        listView.setOnItemLongClickListener { _, _, position, _ ->
            removeItem(position)
            true
        }
    }

    private fun addItem() {
        val itemName = etItem.text.toString().trim()

        if (itemName.isNotEmpty()) {

            listViewModel.items.add(itemName)
        }

        etItem.text.clear()
        updateList()
    }

    private fun removeItem(position: Int) {
        listViewModel.items.removeAt(position)
        updateList()
    }

    private fun updateList() {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            listViewModel.items
        )
        listView.adapter = adapter
    }
}



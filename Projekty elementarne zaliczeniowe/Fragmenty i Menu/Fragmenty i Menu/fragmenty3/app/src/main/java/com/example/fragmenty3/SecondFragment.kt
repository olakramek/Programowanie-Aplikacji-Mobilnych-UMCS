package com.example.fragmenty3

import SharedViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.fragmenty3.databinding.FragmentFirstBinding
import com.example.fragmenty3.databinding.FragmentSecondBinding


class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!

    private val sharedViewModel by activityViewModels<SharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            sharedViewModel.addRandomNumber()
            sharedViewModel.sortSecondFragmentDescending()
            updateNumberList()
        }
    }

    private fun updateNumberList() {
        binding.textviewSecondNumbers.text = sharedViewModel.numberList.toString()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
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
import com.example.fragmenty3.databinding.FragmentFourthBinding

class FourthFragment : Fragment() {

    private var _binding: FragmentFourthBinding? = null


    private val binding get() = _binding!!

    private val sharedViewModel by activityViewModels<SharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFourthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFourth.setOnClickListener {
            sharedViewModel.addRandomNumber()
            sharedViewModel.sortFourthFragmentDescendingOdd() // Sort descending odd numbers
            sharedViewModel.sortFourthFragmentDescendingEven() // Sort descending even numbers
            updateNumberList()
        }
    }

    private fun updateNumberList() {
        binding.textviewFourthNumbers.text = sharedViewModel.numberList.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
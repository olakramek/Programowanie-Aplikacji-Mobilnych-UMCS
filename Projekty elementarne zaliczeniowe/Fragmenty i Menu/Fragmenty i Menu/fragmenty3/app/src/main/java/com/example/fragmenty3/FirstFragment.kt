package com.example.fragmenty3

import SharedViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.fragmenty3.databinding.FragmentFirstBinding



class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel by activityViewModels<SharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            sharedViewModel.addRandomNumber()
            updateNumberList()
        }

        setFragmentResultListener("requestKey") { key, bundle ->
            val sumResult = bundle.getInt("sumResult", 0)
            binding.textViewResult.text = "Sum: $sumResult"
        }

        binding.buttonShowSummary.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SummaryFragment)
        }
    }

    private fun updateNumberList() {
        binding.textviewFirstNumbers.text = sharedViewModel.numberList.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

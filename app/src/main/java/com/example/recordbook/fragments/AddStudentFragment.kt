package com.example.recordbook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.recordbook.databinding.FragmentAddStudentBinding
import com.example.recordbook.misc.RepositoryViewModal

class AddStudentFragment : Fragment() {

    private lateinit var binding: FragmentAddStudentBinding
    private val viewModel: RepositoryViewModal by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            buttonConfirm.setOnClickListener {
                viewModel.addStudent(editTextName.text.toString(),"123123")
                it.findNavController().popBackStack()
            }
        }
    }

}
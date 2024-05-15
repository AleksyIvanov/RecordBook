package com.example.recordbook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.recordbook.databinding.FragmentAddSubjectBinding
import com.example.recordbook.misc.RepositoryViewModal


class AddSubjectFragment : Fragment() {
    private lateinit var binding: FragmentAddSubjectBinding
    private val viewModel: RepositoryViewModal by activityViewModels()
    private val idStudent:Long
        get() = requireArguments().getLong("id")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddSubjectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            buttonConfirm.setOnClickListener {
                viewModel.addSubject(idStudent, editTextName.text.toString())
                it.findNavController().popBackStack()
            }
        }
    }

    companion object {

        @JvmStatic
        fun setId(id: Long):Bundle
        {
            return bundleOf("id" to id)
        }

    }
}
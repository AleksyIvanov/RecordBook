package com.example.recordbook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.recordbook.R
import com.example.recordbook.databinding.FragmentListStudentBinding
import com.example.recordbook.misc.RepositoryViewModal
import com.example.recordbook.misc.Student
import com.example.recordbook.misc.StudentAdapter

class ListStudentFragment : Fragment() {
    private lateinit var binding: FragmentListStudentBinding
    private val viewModel: RepositoryViewModal by activityViewModels()
    private val idStudent:Long
        get() = requireArguments().getLong("id")
        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding)
        {
            val adapter = StudentAdapter(object : StudentAdapter.Listener{
                override fun remove(student: Student) {
                    viewModel.removeStudent(student.id)
                }
            }, true)

            recyclerVew.adapter = adapter

            viewModel.data.observe(viewLifecycleOwner){ list ->
                adapter.submitList(list.filter { !it.teacher })
            }
            buttonBack.setOnClickListener {
                it.findNavController().popBackStack()
            }
            buttonAdd.setOnClickListener {
                it.findNavController().navigate(R.id.action_listStudentFragment_to_addStudentFragment)
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
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
import com.example.recordbook.databinding.FragmentListSubjectBinding
import com.example.recordbook.misc.RepositoryViewModal
import com.example.recordbook.misc.Subject
import com.example.recordbook.misc.SubjectAdapter

class ListSubjectFragment : Fragment() {
    private lateinit var binding: FragmentListSubjectBinding
    private val viewModel: RepositoryViewModal by activityViewModels()
    private val idStudent:Long
        get() = requireArguments().getLong("id")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListSubjectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding)
        {
            val adapter = SubjectAdapter(object :SubjectAdapter.Listener{
                override fun remove(subject: Subject) {
                    viewModel.removeSubject(subject.id, idStudent)
                }
            })

            recyclerVew.adapter = adapter

            viewModel.data.observe(viewLifecycleOwner){list->
                adapter.submitList(list.first{it.id == idStudent}.subjects)
            }

            textViewStudentName.text = if(idStudent!=0L) viewModel.data.value?.find { it.id == idStudent }?.name else "ГЛАВА МИРА"
            buttonAdd.setOnClickListener {
                it.findNavController().navigate(R.id.action_listSubjectFragment_to_addSubjectFragment, AddSubjectFragment.setId(idStudent))
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
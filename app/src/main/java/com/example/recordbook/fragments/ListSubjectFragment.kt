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
    private val isTeacher:Boolean
        get() = requireArguments().getBoolean("isTeacher")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListSubjectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //isTeacher = viewModel.data.value?.first{it.id == idStudent}?.teacher == true
        with(binding)
        {
            val adapter = SubjectAdapter(object :SubjectAdapter.Listener{
                override fun remove(subject: Subject) {
                    viewModel.removeSubject(subject.id, idStudent)
                }

                override fun setRating(subject: Subject, rating: Int) {
                    viewModel.setRating(subject.id,idStudent,rating)
                }
            }, isTeacher)

            if (isTeacher)
                buttonAdd.visibility = View.VISIBLE
            else
                buttonAdd.visibility = View.INVISIBLE


            recyclerVew.adapter = adapter

            viewModel.data.observe(viewLifecycleOwner){list->
                adapter.submitList(list.first{it.id == idStudent }.subjects)
            }
            buttonBack.setOnClickListener {
                it.findNavController().popBackStack()
            }
            textViewStudentName.text = viewModel.data.value?.find { it.id == idStudent }?.name
            buttonAdd.setOnClickListener {
                it.findNavController().navigate(R.id.action_listSubjectFragment_to_addSubjectFragment, AddSubjectFragment.setId(idStudent))
            }
        }
    }

    companion object {
        @JvmStatic
        fun setBundle(id: Long,isTeacher: Boolean):Bundle
        {
            return bundleOf("id" to id,"isTeacher" to isTeacher )
        }

    }
}
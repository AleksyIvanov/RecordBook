package com.example.recordbook.misc

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recordbook.R
import com.example.recordbook.databinding.ItemStudentBinding
import com.example.recordbook.fragments.ListSubjectFragment

class StudentDiffCallback : DiffUtil.ItemCallback<Student>(){
    override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
        return  oldItem == newItem
    }

}
class StudentViewHolder(private val binding: ItemStudentBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(student: Student, listener: StudentAdapter.Listener) {
        binding.apply {
            textViewStudentName.text = student.name
            var count = 0
            var rating = 0
            student.subjects.forEach {
                rating+=it.rating
                count++
            }
            try {
                rating /= count
            }catch (exception:Exception){
               Log.e("Math","Делить на ноль нельзя")
            }
            textViewRating.text = rating.toString()
            buttonRemove.setOnClickListener {
                listener.remove(student)
            }

            root.setOnClickListener {
                it.findNavController().navigate(R.id.action_listStudentFragment_to_listSubjectFragment, ListSubjectFragment.setId(student.id))
            }
        }
    }
}

class StudentAdapter(private val listener: Listener): ListAdapter<Student, StudentViewHolder>(StudentDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }
    override fun onBindViewHolder(holder: StudentViewHolder, position:Int){
        holder.bind(getItem(position),listener)
    }
    interface Listener{
        fun remove(student: Student)
    }
}
package com.example.recordbook.misc

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recordbook.databinding.ItemSubjectBinding

class SubjectDiffCallback : DiffUtil.ItemCallback<Subject>(){
    override fun areItemsTheSame(oldItem: Subject, newItem: Subject): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: Subject, newItem: Subject): Boolean {
        return  oldItem == newItem
    }

}
class SubjectViewHolder(private val binding: ItemSubjectBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(subject: Subject,listener: SubjectAdapter.Listener) {
        binding.apply {
            textViewSubject.text = subject.name
            textViewRating.text = subject.rating.toString()
            buttonRemove.setOnClickListener {
                listener.remove(subject)
            }
        }
    }
}

class SubjectAdapter(private val listener:Listener): ListAdapter<Subject, SubjectViewHolder>(SubjectDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val binding = ItemSubjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubjectViewHolder(binding)
    }
    override fun onBindViewHolder(holder: SubjectViewHolder, position:Int){
        holder.bind(getItem(position),listener)
    }
    interface Listener{
        fun remove(subject: Subject)
    }
}
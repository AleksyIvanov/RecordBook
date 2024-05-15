package com.example.recordbook.misc

import android.view.LayoutInflater
import android.view.View
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
    fun bind(subject: Subject,listener: SubjectAdapter.Listener, isTeacher:Boolean) {
        binding.apply {
            textViewSubject.text = subject.name
            textViewRating.text = subject.rating.toString()
            if (isTeacher) {
                textViewRating.visibility = View.INVISIBLE
                buttonRemove.visibility = View.VISIBLE
                buttonRating2.visibility = View.VISIBLE
                buttonRating3.visibility = View.VISIBLE
                buttonRating4.visibility = View.VISIBLE
                buttonRating5.visibility = View.VISIBLE
            }else{
                textViewRating.visibility = View.VISIBLE
                buttonRemove.visibility = View.INVISIBLE
                buttonRating2.visibility = View.INVISIBLE
                buttonRating3.visibility = View.INVISIBLE
                buttonRating4.visibility = View.INVISIBLE
                buttonRating5.visibility = View.INVISIBLE
            }
            if (subject.rating != 0){
                textViewRating.visibility = View.VISIBLE
                buttonRating2.visibility = View.INVISIBLE
                buttonRating3.visibility = View.INVISIBLE
                buttonRating4.visibility = View.INVISIBLE
                buttonRating5.visibility = View.INVISIBLE
            }
            buttonRating2.setOnClickListener {
                listener.setRating(subject, 2)
                textViewRating.text = "2"
                textViewRating.visibility = View.VISIBLE
                buttonRating2.visibility = View.INVISIBLE
                buttonRating3.visibility = View.INVISIBLE
                buttonRating4.visibility = View.INVISIBLE
                buttonRating5.visibility = View.INVISIBLE
            }
            buttonRating3.setOnClickListener {
                listener.setRating(subject, 3)
                textViewRating.text = "3"
                textViewRating.visibility = View.VISIBLE
                buttonRating2.visibility = View.INVISIBLE
                buttonRating3.visibility = View.INVISIBLE
                buttonRating4.visibility = View.INVISIBLE
                buttonRating5.visibility = View.INVISIBLE
            }
            buttonRating4.setOnClickListener {
                listener.setRating(subject, 4)
                textViewRating.text = "4"
                textViewRating.visibility = View.VISIBLE
                buttonRating2.visibility = View.INVISIBLE
                buttonRating3.visibility = View.INVISIBLE
                buttonRating4.visibility = View.INVISIBLE
                buttonRating5.visibility = View.INVISIBLE
            }
            buttonRating5.setOnClickListener {
                listener.setRating(subject, 5)
                textViewRating.text = "5"
                textViewRating.visibility = View.VISIBLE
                buttonRating2.visibility = View.INVISIBLE
                buttonRating3.visibility = View.INVISIBLE
                buttonRating4.visibility = View.INVISIBLE
                buttonRating5.visibility = View.INVISIBLE
            }

            buttonRemove.setOnClickListener {
                listener.remove(subject)
            }

        }
    }
}

class SubjectAdapter(private val listener:Listener, private val isTeacher: Boolean): ListAdapter<Subject, SubjectViewHolder>(SubjectDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val binding = ItemSubjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubjectViewHolder(binding)
    }
    override fun onBindViewHolder(holder: SubjectViewHolder, position:Int){
        holder.bind(getItem(position),listener,isTeacher)
    }
    interface Listener{
        fun remove(subject: Subject)
        fun setRating(subject: Subject, rating:Int)
    }
}
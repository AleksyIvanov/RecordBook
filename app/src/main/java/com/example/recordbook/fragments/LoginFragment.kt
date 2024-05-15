package com.example.recordbook.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.recordbook.R
import com.example.recordbook.databinding.FragmentLoginBinding
import com.example.recordbook.misc.Student
import com.example.recordbook.misc.Subject
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class LoginFragment : Fragment() {
   private lateinit var binding: FragmentLoginBinding
    //private lateinit var database: FirebaseDatabase
    private lateinit var database: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //database = FirebaseDatabase.getInstance()
        database = Firebase.database.reference

        database.child("Student").child("0").setValue(
            Student(
                id = 0,
                name = "Господин Сорокин",
                password = "123123",
                subjects = listOf(
                    Subject(
                    id = 0,
                    name = "Английский",
                    rating = 5),
                    Subject(
                        id = 1,
                        name = "Немецкий",
                        rating = 5),
                )
            )
        )
/*
        database.child("Student").get().addOnSuccessListener {
            if (it.exists())
                for (user in it.children)
                    if (user.getValue<Student>()?.name == binding.editTextName.text.toString() && user.getValue<Student>()?.password == binding.editTextPassword.text.toString())
                        if (user.getValue<Student>()?.teacher == true)
                            view.findNavController().navigate(R.id.action_loginFragment_to_listStudentFragment)
                        else
                            view.findNavController().navigate(R.id.action_loginFragment_to_listSubjectFragment, ListSubjectFragment.setId(user.key.toString().toLong()))

        }

*/
      /*  with(binding)
        {
            buttonConfirm.setOnClickListener {view->
                database.signInWithEmailAndPassword(editTextName.text.toString(), editTextPassword.text.toString()).addOnCompleteListener(requireActivity()) {
                    if (it.isSuccessful) {
                        view.findNavController().navigate(R.id.action_loginFragment_to_listStudentFragment)
                    } else
                        Toast.makeText(requireContext(), "Log In failed ", Toast.LENGTH_SHORT).show()
                }

            }
        }*/
    }

}
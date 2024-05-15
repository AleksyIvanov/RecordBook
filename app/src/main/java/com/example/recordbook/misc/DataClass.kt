package com.example.recordbook.misc
data class Student(
    val id:Long = 0,
    val name:String = "",
    val password:String = "",
    var subjects: List<Subject> = emptyList(),
    var teacher:Boolean = false
)

data class Subject(
    val id:Long = 0,
    val name:String = "",
    var rating:Int = 0
)

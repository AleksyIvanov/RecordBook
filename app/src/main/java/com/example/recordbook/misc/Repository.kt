package com.example.recordbook.misc

import android.app.Application
import android.content.Context
import android.media.Rating
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.database.database

interface ListenerRepository {
    fun getAll(): LiveData<List<Student>>
    fun setStudents(students:List<Student>)
    fun setRating(idSubject: Long, idStudent: Long, rating: Int)
    fun addSubject(idStudent:Long,name:String)
    fun addStudent(name: String, password: String)
    fun removeSubject(idSubject: Long,idStudent: Long )
    fun removeStudent(idStudent: Long)
}
class RepositoryInMemoryImpl(context: Context):ListenerRepository {
    private var students:List<Student> = listOf()
    private val data = MutableLiveData(students)
    private val database = Firebase.database.reference
    private var nextId = 0L

    private fun sync() {
        data.value = students
        database.removeValue()
        database.setValue(students)
    }
    override fun getAll(): LiveData<List<Student>> = data
    override fun setStudents(students: List<Student>) {
        this.students = students
        sync()
    }

    override fun setRating(idSubject: Long, idStudent: Long, rating: Int) {
        students.map { student ->
            if (student.id==idStudent) student.subjects.map {
                if(it.id == idSubject) it.rating = rating
                it
            }
            student
        }
        sync()
    }

    override fun addSubject(idStudent: Long, name: String) {
        students.map {
            if (it.id == idStudent)
                it.subjects += listOf(
                    Subject(
                        id = getNewId(it.subjects),
                        name = name,
                        rating = 0
                    )
                )
        }
        sync()
    }

    private fun getNewId(list: List<Subject>):Long {
        var id = 0L
        list.forEach {
            if (it.id == id) id++
        }
        return id
    }
    private fun getNewIdStudent(list: List<Student>):Long {
        var id = 0L
        list.forEach {
            if (it.id == id) id++
        }
        return id
    }
    override fun addStudent(name: String, password: String) {
        students += listOf(Student(id = getNewIdStudent(students),name = name, password = password, subjects = emptyList() ))
        sync()
    }

    override fun removeSubject(idSubject: Long, idStudent: Long) {
        students.map { student ->
            if (student.id == idStudent)
                student.subjects = student.subjects.filter {
                    it.id != idSubject

                }
            student
        }
        sync()
    }

    override fun removeStudent(idStudent: Long) {
        students = students.filter {
            it.id != idStudent
        }
        sync()
    }

}
class RepositoryViewModal(application: Application): AndroidViewModel(application) {

    private val repository: RepositoryInMemoryImpl = RepositoryInMemoryImpl(application)
    val data = repository.getAll()
    fun setStudent(students: List<Student>) = repository.setStudents(students)
    fun addStudent(name: String, password: String) = repository.addStudent(name,password)
    fun addSubject(idStudent: Long, name: String) = repository.addSubject(idStudent,name)
    fun removeStudent(idStudent: Long) = repository.removeStudent(idStudent)
    fun removeSubject(idSubject: Long, idStudent: Long) =repository.removeSubject(idSubject,idStudent)
    fun setRating(idSubject: Long, idStudent: Long, rating: Int) =repository.setRating(idSubject,idStudent,rating)
}
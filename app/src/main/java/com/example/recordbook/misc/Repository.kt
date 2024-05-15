package com.example.recordbook.misc

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface ListenerRepository {
    fun getAll(): LiveData<List<Student>>
    fun addSubject(idStudent:Long,name:String)
    fun addStudent(name: String, password: String)
    fun removeSubject(idSubject: Long,idStudent: Long )
    fun removeStudent(idStudent: Long)
}
class RepositoryInMemoryImpl(context: Context):ListenerRepository {
    private var students:List<Student> = listOf()
    private val data = MutableLiveData(students)
    private var nextId = 0L

    private fun sync() {
        data.value = students
    }
    override fun getAll(): LiveData<List<Student>> = data
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
    override fun addStudent(name: String, password: String) {
        students += listOf(Student(id = nextId++,name = name, password = password,emptyList() ))
        sync()
    }

    override fun removeSubject(idSubject: Long, idStudent: Long) {
        students.map { student ->
            if (student.id == idSubject)
                student.subjects.filter {
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
    fun addStudent(name: String, password: String) = repository.addStudent(name,password)
    fun addSubject(idStudent: Long, name: String) = repository.addSubject(idStudent,name)
    fun removeStudent(idStudent: Long) = repository.removeStudent(idStudent)
    fun removeSubject(idSubject: Long, idStudent: Long) =repository.removeSubject(idSubject,idStudent)
}
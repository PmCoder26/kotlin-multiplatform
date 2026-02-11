package com.parimal.kotlin_multiplatform.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class StudentViewModel : ViewModel() {

    private val _studentList = mutableStateListOf<Student>()

    val studentList = _studentList


    fun addStudent(student: Student) {
        _studentList.add(student)
    }

}
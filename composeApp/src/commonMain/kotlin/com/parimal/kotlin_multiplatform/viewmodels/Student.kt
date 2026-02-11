package com.parimal.kotlin_multiplatform.viewmodels

data class Student(
    val rollNo: Int,
    val name: String = "New student",
    val className: String = "Class Name",
    val division: String = "Division Name"
)

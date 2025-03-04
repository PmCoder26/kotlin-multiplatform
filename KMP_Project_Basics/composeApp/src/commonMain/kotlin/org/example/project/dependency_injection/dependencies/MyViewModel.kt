package org.example.project.dependency_injection.dependencies

import androidx.lifecycle.ViewModel

class MyViewModel(
    private val myRepository: MyRepository
) : ViewModel() {

    fun getHelloWorldString(): String {
        return myRepository.helloWorld()
    }

}
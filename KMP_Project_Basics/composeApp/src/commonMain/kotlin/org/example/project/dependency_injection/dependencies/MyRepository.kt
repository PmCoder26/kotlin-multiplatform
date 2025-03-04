package org.example.project.dependency_injection.dependencies

interface MyRepository {
    fun helloWorld(): String
}


class MyRepositoryImpl(
    private val dbClient: DBClient
): MyRepository {
    override fun helloWorld(): String {
        return "Hello World!"
    }
}
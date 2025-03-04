package org.example.project.dependency_injection.di

import org.example.project.dependency_injection.dependencies.DBClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule = module {
    singleOf(::DBClient)
}
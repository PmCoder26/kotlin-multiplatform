package org.example.project.dependency_injection.di

import org.example.project.dependency_injection.dependencies.MyRepository
import org.example.project.dependency_injection.dependencies.MyRepositoryImpl
import org.example.project.dependency_injection.dependencies.MyViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {

    singleOf(::MyRepositoryImpl).bind<MyRepository>()

    singleOf(::MyViewModel)

}
package com.parimal.kotlin_multiplatform.dependency_injection


import com.parimal.kotlin_multiplatform.viewmodels.StudentViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


val appModule = module {

    viewModelOf(::StudentViewModel)

}


fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {

    appDeclaration()

    modules(appModule)

}
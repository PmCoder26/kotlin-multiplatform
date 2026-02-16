package com.parimal.kotlin_multiplatform.dependency_injection

import com.parimal.kotlin_multiplatform.datastore.DATA_STORE_FILE_NAME
import com.parimal.kotlin_multiplatform.datastore.createDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {

    single {
        createDataStore { DATA_STORE_FILE_NAME }
    }

}

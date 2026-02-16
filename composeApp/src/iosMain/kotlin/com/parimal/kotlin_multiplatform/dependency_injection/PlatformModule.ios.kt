package com.parimal.kotlin_multiplatform.dependency_injection

import com.parimal.kotlin_multiplatform.datastore.DATA_STORE_FILE_NAME
import com.parimal.kotlin_multiplatform.datastore.createDataStore
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual val platformModule: Module = module {

    single {
        createDataStore {
            val directory = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null
            )
            requireNotNull(directory).path + "/$DATA_STORE_FILE_NAME"
        }
    }

}
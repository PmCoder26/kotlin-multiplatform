package org.example.project.dependency_injection.dependencies

import android.content.Context

actual class DBClient(
    private val context: Context
)
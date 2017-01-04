package com.antyzero.smoksmog.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build


class PermissionHelper(private val context: Context) {

    val isGrantedLocationCorsare: Boolean
        get() = isGranted(Manifest.permission.ACCESS_COARSE_LOCATION)

    private fun isGranted(permission: String): Boolean {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
        } else {
            return true
        }
    }
}

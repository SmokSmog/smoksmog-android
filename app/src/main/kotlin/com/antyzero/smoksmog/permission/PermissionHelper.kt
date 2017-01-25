package com.antyzero.smoksmog.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.content.ContextCompat


class PermissionHelper(private val context: Context) {

    val isGrantedAccessCoarseLocation: Boolean
        get() = isGranted(Manifest.permission.ACCESS_COARSE_LOCATION)

    fun get(permissionKey: String) = isGranted(permissionKey)

    private fun isGranted(permission: String): Boolean {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        } else {
            return true // TODO check manifest
        }
    }
}

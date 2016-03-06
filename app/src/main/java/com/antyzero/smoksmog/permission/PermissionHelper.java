package com.antyzero.smoksmog.permission;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;


public class PermissionHelper {

    private final Context context;

    public PermissionHelper( Context context ) {
        this.context = context;
    }

    public boolean isGrantedLocationCorsare() {
        return isGranted( Manifest.permission.ACCESS_COARSE_LOCATION );
    }

    private boolean isGranted( String permission ) {
        //noinspection SimplifiableIfStatement
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            return context.checkSelfPermission( permission ) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }
}

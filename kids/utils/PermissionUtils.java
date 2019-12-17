package com.example.kids.utils;

import android.app.Activity;
import android.content.pm.PackageManager;

import java.util.ArrayList;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtils {

    public static boolean checkAndAskPermission(
            Activity activity,
            int requestCode,
            String... permissions
    ) {
        ArrayList<String> neededPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                neededPermissions.add(permission);
            }
        }
        if (neededPermissions.size() == 0) {
            return true;
        } else {
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
        }
        return false;
    }

}

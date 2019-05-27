package com.dev.flicker;

import android.content.Context;
import android.widget.Toast;

public class AppUtil {
    public static void showToast(Context mContext, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }
}

package com.dev.flicker.fullscreen;


import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.dev.flicker.R;
import com.dev.flicker.databinding.FullscreenDialogBinding;

public class FullScreenBannerDialog extends DialogFragment {
    public static final String KEY_BITMAP = "DIALOG_KEY_BITMAP";
    public static String TAG = FullScreenBannerDialog.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        final FullscreenDialogBinding viewDataBinding =
                DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fullscreen_dialog, container, false);
        viewDataBinding.setBannerDialog(this);
        if (null != getArguments()) {
            Bitmap bitmap = getArguments().getParcelable(KEY_BITMAP);
            if (null != getContext() && null != bitmap) {
                viewDataBinding.ivBanner.setImageBitmap(bitmap);
            }
        }

        return viewDataBinding.getRoot();
    }


    public void onDissmissDialog() {
        dismiss();
    }
}

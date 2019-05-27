package com.dev.flicker.callback;

import android.view.View;

public interface OnItemClickListener<T> {
    void onItemClick(View v, T model);
}

package com.dev.flicker.callback;

import java.util.List;

public interface OnLoadItems<T> {
    void onLoading();

    void onSuccess(List<T> itemsList);

    void onFailure(Throwable e);
}

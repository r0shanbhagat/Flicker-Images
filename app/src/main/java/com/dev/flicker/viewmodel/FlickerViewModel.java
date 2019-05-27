package com.dev.flicker.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.dev.flicker.beans.FlickerModel;
import com.dev.flicker.callback.OnLoadItems;
import com.dev.flicker.model.FlickerDataModel;

import java.util.ArrayList;
import java.util.List;

public class FlickerViewModel extends ViewModel {

    private FlickerDataModel dataModel;

    public FlickerViewModel() {
        dataModel = new FlickerDataModel();
    }

    /**
     * @param mContext
     * @return
     */
    public List<FlickerModel> getImageThumbnailList(Context mContext) {
        return new ArrayList<>();
    }


    public void loadImagesFromServer(Context mContext, String tag, OnLoadItems onLoadItems) {
        dataModel.getThumbnailList(mContext, tag, onLoadItems);
    }
}
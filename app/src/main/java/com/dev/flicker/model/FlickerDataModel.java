package com.dev.flicker.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.dev.flicker.beans.FlickerModel;
import com.dev.flicker.callback.OnLoadItems;
import com.dev.flicker.network.ApiService;
import com.dev.flicker.network.ServiceGenerator;
import com.dev.flicker.network.beans.FlickerImageResponse;
import com.dev.flicker.network.beans.Item;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FlickerDataModel {

    public static final String TAG = FlickerDataModel.class.getSimpleName();


    /**
     * @param mContext
     * @param tag
     * @param onLoadItems
     * @return
     */
    public void getThumbnailList(Context mContext, String tag, final OnLoadItems onLoadItems) {
        onLoadItems.onLoading();
        ApiService service = ServiceGenerator.getClient(mContext).create(ApiService.class);
        HashMap<String, String> mParamMap = new HashMap<>();
        mParamMap.put("format", "json");
        mParamMap.put("nojsoncallback", "?");
        if (!TextUtils.isEmpty(tag)) {
            mParamMap.put("tags", tag);
        }
        service.getImageList(mParamMap)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<FlickerImageResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FlickerImageResponse response) {
                        final List<Item> imageList = response.getItems();
                        if (null != imageList && !imageList.isEmpty()) {
                            List<FlickerModel> flickerModelList = new ArrayList<>();
                            for (final Item item : imageList) {
                                try {
                                    URL url = new URL(item.getMedia().getM());
                                    Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                    FlickerModel flickerModel = new FlickerModel();
                                    flickerModel.setName(item.getTitle());
                                    flickerModel.setThumbnail(image);
                                    flickerModelList.add(flickerModel);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            onLoadItems.onSuccess(flickerModelList);
                        } else {
                            onLoadItems.onFailure(new Exception());
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        onLoadItems.onFailure(e);
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        //  cPresenter.updateCoinList(allCurrencyList);
                    }
                });
    }


}
package com.dev.flicker.imagelisting;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dev.flicker.AppUtil;
import com.dev.flicker.R;
import com.dev.flicker.beans.FlickerModel;
import com.dev.flicker.callback.OnItemClickListener;
import com.dev.flicker.callback.OnLoadItems;
import com.dev.flicker.databinding.ActivityImageListingBinding;
import com.dev.flicker.fullscreen.FullScreenBannerDialog;
import com.dev.flicker.imagelisting.adapter.FlickerListAdapter;
import com.dev.flicker.viewmodel.FlickerViewModel;

import java.util.ArrayList;
import java.util.List;

public class ImageListingActivity extends AppCompatActivity {
    public static final String TAG = ImageListingActivity.class.getSimpleName();
    private ActivityImageListingBinding binding;
    private FlickerListAdapter adapter;
    private FlickerViewModel viewModel;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_listing);
        viewModel = ViewModelProvider.AndroidViewModelFactory.
                getInstance(getApplication()).create(FlickerViewModel.class);
        setupToolbar();
        setUpList();
        doFetchImageList("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ic_action_menu, menu);
        MenuItem mSearch = menu.findItem(R.id.menu_app_bar_search);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search by tag");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                doFetchImageList(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                doFetchImageList("");
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void doFetchImageList(String tag) {
        viewModel.loadImagesFromServer(this, tag, new OnLoadItems<FlickerModel>() {
            @Override
            public void onLoading() {
                progressDialog = new ProgressDialog(ImageListingActivity.this);
                progressDialog.setMessage(getString(R.string.loading_message));
                progressDialog.show();
            }

            @Override
            public void onSuccess(final List<FlickerModel> itemList) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.getItemList().clear();
                        adapter.getItemList().addAll(itemList);
                        adapter.notifyDataSetChanged();
                        showNoDataFound();
                        dismissDialog();

                    }
                });

            }

            @Override
            public void onFailure(Throwable e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.getItemList().clear();
                        showNoDataFound();
                        dismissDialog();
                        AppUtil.showToast(ImageListingActivity.this, getResources().getString(R.string.error_msg));
                    }
                });

            }
        });
    }

    private void dismissDialog() {
        if (null != progressDialog) {
            progressDialog.dismiss();
        }
    }


    /**
     * Preparing List
     */
    private void setUpList() {
        adapter = new FlickerListAdapter(new ArrayList<FlickerModel>());
        adapter.setOnItemClickListener(new OnItemClickListener<FlickerModel>() {
            @Override
            public void onItemClick(View v, FlickerModel model) {
                redirectToFullScreen(model);
            }
        });
        binding.rvTodo.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rvTodo.setAdapter(adapter);
        showNoDataFound();
    }

    private void redirectToFullScreen(FlickerModel model) {
        FullScreenBannerDialog fragment = new FullScreenBannerDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable(FullScreenBannerDialog.KEY_BITMAP, model.getThumbnail());
        fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(fragment, TAG);
        ft.commitAllowingStateLoss();
    }

    private void showNoDataFound() {
        binding.tvNoDataFound.setVisibility(adapter.getItemList().isEmpty() ? View.VISIBLE : View.GONE);
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (null != getSupportActionBar()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

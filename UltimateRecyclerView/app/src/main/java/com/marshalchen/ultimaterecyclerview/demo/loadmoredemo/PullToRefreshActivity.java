package com.marshalchen.ultimaterecyclerview.demo.loadmoredemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.marshalchen.ultimaterecyclerview.URLogs;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.demo.griddemo.TypedAdapter;
import com.marshalchen.ultimaterecyclerview.demo.modules.JRitem;
import com.marshalchen.ultimaterecyclerview.demo.modules.SampleDataboxset;
import com.marshalchen.ultimaterecyclerview.demo.rvComponents.sectionZeroAdapter;
import com.marshalchen.ultimaterecyclerview.demo.modules.FastBinding;

import java.util.ArrayList;
import java.util.List;

import com.insraincubeptr.PtrDefaultHandler;
import com.insraincubeptr.PtrFrameLayout;
import com.insraincubeptr.PtrHandler;
import com.insraincubeptr.PtrUIHandler;
import com.insraincubeptr.header.MaterialHeader;
import com.insraincubeptr.header.StoreHouseHeader;
import com.insraincubeptr.indicator.PtrIndicator;


public class PullToRefreshActivity extends BasicFunctions implements ActionMode.Callback {

    private UltimateRecyclerView ultimateRecyclerView;
    private sectionZeroAdapter simpleRecyclerViewAdapter = null;
    private TypedAdapter mGridAdapter;
    private GridLayoutManager mGridLayoutManager;

    @Override
    protected void onLoadmore() {

    }

    @Override
    protected void onFireRefresh() {
        //  simpleRecyclerViewAdapter.insertLast("Refresh things");
        //  ultimateRecyclerView.scrollBy(0, -50);
        //  linearLayoutManager.scrollToPosition(0);
        ultimateRecyclerView.mPtrFrameLayout.refreshComplete();
        changeHeaderHandler.sendEmptyMessageDelayed(0, 500);
    }

    @Override
    protected void addButtonTrigger() {

    }

    @Override
    protected void removeButtonTrigger() {

    }

    private void setup() {
        mGridAdapter = new TypedAdapter();
        mGridLayoutManager = new GridLayoutManager(this, 3);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mGridAdapter.getItemViewType(position) == UltimateViewAdapter.VIEW_TYPES.NORMAL) {
                    return 1;
                } else {
                    return mGridLayoutManager.getSpanCount();
                }
            }
        });
        ultimateRecyclerView.setLoadMoreView(R.layout.custom_bottom_progressbar);
        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, int maxLastVisiblePosition) {
                //   Log.d(TAG, itemsCount + " :: " + itemsCount);
                ultimateRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mGridAdapter.insert(SampleDataboxset.genJRList(5));
                    }
                }, 2000);
            }
        });
        ultimateRecyclerView.setHasFixedSize(true);
        ultimateRecyclerView.setSaveEnabled(true);
        ultimateRecyclerView.setClipToPadding(true);
        ultimateRecyclerView.setNormalHeader(setupHeaderView());
        ultimateRecyclerView.setLayoutManager(mGridLayoutManager);
        ultimateRecyclerView.setAdapter(mGridAdapter);
        ultimateRecyclerView.setRefreshing(true);
        ultimateRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private List<JRitem> getJRList() {
        List<JRitem> team = new ArrayList<>();
        //you can make your own test for starting-zero-data
        team = SampleDataboxset.genJRList(4);
        return team;
    }

    private View setupHeaderView() {
        return LayoutInflater.from(this).inflate(R.layout.header_love, null, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_refresh_activity);
        ultimateRecyclerView = (UltimateRecyclerView) findViewById(R.id.custom_ultimate_recycler_view);
        //refreshingMaterial();
        setup();
        refreshingString();
    }

    void refreshingString() {
        storeHouseHeader = new StoreHouseHeader(this);
        storeHouseHeader.setPadding(0, 150, 0, 150);
        // storeHouseHeader.setPadding(0, LocalDisplay.dp2px(20), 0, LocalDisplay.dp2px(20));
        storeHouseHeader.initWithString("BIG SEXY MAMA");
        //storeHouseHeader.initWithStringArray(R.array.akta);
        // ultimateRecyclerView.mPtrFrameLayout.removePtrUIHandler(materialHeader);
        ultimateRecyclerView.mPtrFrameLayout.setHeaderView(storeHouseHeader);
        ultimateRecyclerView.mPtrFrameLayout.addPtrUIHandler(storeHouseHeader);
       // ultimateRecyclerView.mPtrFrameLayout.autoRefresh(false);
        ultimateRecyclerView.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view2) {
                return PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, view, view2);
                // return true;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mGridAdapter.insert(getJRList());
                        //ultimateRecyclerView.mPtrFrameLayout.refreshComplete();
                        // onFireRefresh();
                    }
                }, 2000);
            }
        });
        ultimateRecyclerView.mPtrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ultimateRecyclerView.mPtrFrameLayout.autoRefresh();
            }
        }, 100);
    }
/*

    void refreshingRental() {
        rentalsSunHeaderView = new RentalsSunHeaderView(this);
        rentalsSunHeaderView.setUp(ultimateRecyclerView.mPtrFrameLayout);
        ultimateRecyclerView.mPtrFrameLayout.removePtrUIHandler(materialHeader);
        ultimateRecyclerView.mPtrFrameLayout.removePtrUIHandler(storeHouseHeader);
        ultimateRecyclerView.mPtrFrameLayout.setHeaderView(rentalsSunHeaderView);
        ultimateRecyclerView.mPtrFrameLayout.addPtrUIHandler(rentalsSunHeaderView);
        ultimateRecyclerView.mPtrFrameLayout.autoRefresh(false);
        ultimateRecyclerView.mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view2) {
                boolean canbePullDown = PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, view, view2);
                return canbePullDown;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        linearLayoutManager.scrollToPosition(0);
                        ultimateRecyclerView.mPtrFrameLayout.refreshComplete();
                        changeHeaderHandler.sendEmptyMessageDelayed(3, 500);
                    }
                },

                1800);
            }
        });

    }
*/

    private void refreshingMaterial() {
        materialHeader = new MaterialHeader(this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        materialHeader.setColorSchemeColors(colors);
        materialHeader.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        materialHeader.setPadding(0, 15, 0, 10);
        materialHeader.setPtrFrameLayout(ultimateRecyclerView.mPtrFrameLayout);
        ultimateRecyclerView.mPtrFrameLayout.autoRefresh(false);
        ultimateRecyclerView.mPtrFrameLayout.removePtrUIHandler(storeHouseHeader);
        ultimateRecyclerView.mPtrFrameLayout.setHeaderView(materialHeader);
        ultimateRecyclerView.mPtrFrameLayout.addPtrUIHandler(materialHeader);

        ultimateRecyclerView.mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return true;
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //  simpleRecyclerViewAdapter.insertLast("Refresh things");
                        //   ultimateRecyclerView.scrollBy(0, -50);
                        //   linearLayoutManager.scrollToPosition(0);
                        ultimateRecyclerView.mPtrFrameLayout.refreshComplete();
                        //   changeHeaderHandler.sendEmptyMessageDelayed(2, 500);
                    }
                }, 1800);
            }
        });
    }

    final Handler changeHeaderHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    refreshingStringArray();
                    break;
                case 1:
                    //  refreshingMaterial();
                    refreshingString();
                    break;
                case 2:
                    // refreshingString();
                    break;
                case 3:
                    refreshingString();
                    break;
                case 4:
                    break;
            }
        }
    };
    private int mLoadTime = 0;
    private StoreHouseHeader storeHouseHeader;
    private MaterialHeader materialHeader;
    //  RentalsSunHeaderView rentalsSunHeaderView;

    private void refreshingStringArray() {
        storeHouseHeader = new StoreHouseHeader(this);
        storeHouseHeader.setPadding(0, 15, 0, 0);

        // using string array from resource xml file
        storeHouseHeader.initWithStringArray(R.array.storehouse);
        ultimateRecyclerView.mPtrFrameLayout.setDurationToCloseHeader(1500);
        ultimateRecyclerView.mPtrFrameLayout.removePtrUIHandler(materialHeader);
        ultimateRecyclerView.mPtrFrameLayout.setHeaderView(storeHouseHeader);
        ultimateRecyclerView.mPtrFrameLayout.addPtrUIHandler(storeHouseHeader);
        ultimateRecyclerView.mPtrFrameLayout.autoRefresh(false);

        // change header after loaded
        ultimateRecyclerView.mPtrFrameLayout.addPtrUIHandler(new PtrUIHandler() {
            @Override
            public void onUIReset(PtrFrameLayout frame) {
                mLoadTime++;
                if (mLoadTime % 2 == 0) {
                    storeHouseHeader.setScale(1);
                    storeHouseHeader.initWithStringArray(R.array.storehouse);
                } else {
                    storeHouseHeader.setScale(0.5f);
                    storeHouseHeader.initWithStringArray(R.array.akta);
                }
            }

            @Override
            public void onUIRefreshPrepare(PtrFrameLayout frame) {

            }

            @Override
            public void onUIRefreshBegin(PtrFrameLayout frame) {

            }

            @Override
            public void onUIRefreshComplete(PtrFrameLayout frame) {

            }

            @Override
            public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

            }
        });

        ultimateRecyclerView.mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return true;
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // frame.refreshComplete();
                        //  simpleRecyclerViewAdapter.insertLast("Refresh things");
                        // ultimateRecyclerView.scrollBy(0, -50);
                        //   linearLayoutManager.scrollToPosition(0);
                        ultimateRecyclerView.mPtrFrameLayout.refreshComplete();
                        if (mLoadTime % 2 == 0) {
                            changeHeaderHandler.sendEmptyMessageDelayed(1, 500);
                        }
                    }
                }, 2000);
            }
        });
    }

    private void toggleSelection(int position) {
        simpleRecyclerViewAdapter.toggleSelection(position);
        actionMode.setTitle("Selected " + "1");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public int getScreenHeight() {
        return findViewById(android.R.id.content).getHeight();
    }

    @Override
    protected void doURV(UltimateRecyclerView urv) {

    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        URLogs.d("actionmode---" + (mode == null));
        mode.getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
        //  return false;
    }

    /**
     * Called to refresh an action mode's action menu whenever it is invalidated.
     *
     * @param mode ActionMode being prepared
     * @param menu Menu used to populate action buttons
     * @return true if the menu or action mode was updated, false otherwise.
     */
    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        // swipeToDismissTouchListener.setEnabled(false);
        this.actionMode = mode;
        return false;
    }


    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }


    @Override
    public void onDestroyActionMode(ActionMode mode) {
        this.actionMode = null;
    }


    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FastBinding.startactivity(this, item.getItemId());
        return super.onOptionsItemSelected(item);
    }


}

package com.marshalchen.ultimaterecyclerview.demo.admobdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

/*import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;*/
import com.marshalchen.ultimaterecyclerview.quickAdapter.AdmobAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.demo.R;
import com.marshalchen.ultimaterecyclerview.demo.modules.SampleDataboxset;
import com.marshalchen.ultimaterecyclerview.quickAdapter.easyRegularAdapter;
import com.marshalchen.ultimaterecyclerview.quickAdapter.BiAdAdapterSwitcher;
import com.ml93.captainmiaoUtil.ui.AdGoogleDisplaySupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hesk on 4/8/15.
 */
public class TestAdvancedAdmobActivity extends AppCompatActivity {
    private UltimateRecyclerView ultimateRecyclerView;
    private boolean admob_test_mode = false;
    private LinearLayoutManager linearLayoutManager;
    private Toolbar toolbar;
    private BiAdAdapterSwitcher bi_sw;

    public static class adap extends AdmobAdapter<RelativeLayout, String, SingleItemHolder> {

        public adap(RelativeLayout v, List<String> L) {
            super(v, false, 13, L, null);
        }


        @Override
        protected int getNormalLayoutResId() {
            return getIdRV();
        }


        @Override
        protected SingleItemHolder newViewHolder(View mview) {
            return new SingleItemHolder(mview, VIEW_TYPES.NORMAL);
        }

        /**
         * binding normal view holder
         *
         * @param holder   holder class
         * @param data     data
         * @param position position
         */
        @Override
        protected void withBindHolder(SingleItemHolder holder, String data, int position) {
            bindthisInhere(holder, data, position);
        }

    }

    public static class regular extends easyRegularAdapter<String, SingleItemHolder> {

        public regular(List list) {
            super(list);
        }

        @Override
        protected int getNormalLayoutResId() {
            return getIdRV();
        }

        @Override
        protected SingleItemHolder newViewHolder(View view) {
            return new SingleItemHolder(view, VIEW_TYPES.NORMAL);
        }

        @Override
        protected void withBindHolder(SingleItemHolder holderm, String data, int position) {
            bindthisInhere(holderm, data, position);
        }
    }

    private static void bindthisInhere(SingleItemHolder d, String data, int pos) {
        d.textViewSample.setText(data);
        d.num.setText("@:" + pos);
    }

    private static int getIdRV() {
        return R.layout.countable_rv_adp;
    }

  /**
   *
   *
   private RelativeLayout createadmob() {

        AdSize adSize = AdSize.SMART_BANNER;

        DisplayMetrics dm = getResources().getDisplayMetrics();

        double density = dm.density * 160;
        double x = Math.pow(dm.widthPixels / density, 2);
        double y = Math.pow(dm.heightPixels / density, 2);
        double screenInches = Math.sqrt(x + y);

        if (screenInches > 8) { // > 728 X 90
            adSize = AdSize.LEADERBOARD;
        } else if (screenInches > 6) { // > 468 X 60
            adSize = AdSize.MEDIUM_RECTANGLE;
        } else { // > 320 X 50
            adSize = AdSize.BANNER;
        }

        adSize = AdSize.MEDIUM_RECTANGLE;
        final AdView mAdView = new AdView(this);
        mAdView.setAdSize(adSize);
        mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        // Create an ad request.
        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
        if (admob_test_mode)
            // Optionally populate the ad request builder.
            adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
        // Start loading the ad.
        mAdView.loadAd(adRequestBuilder.build());
        DisplayMetrics displaymetrics = new DisplayMetrics();
        final RelativeLayout layout = AdGoogleDisplaySupport.initialSupport(this, displaymetrics);
        final double ratio = AdGoogleDisplaySupport.ratioMatching(displaymetrics);
        final int ad_height = AdGoogleDisplaySupport.defaultHeight(displaymetrics);
        AdGoogleDisplaySupport.panelAdjust(mAdView, (int) (ad_height * ratio));
        // get display info
        //  G.display_w = displayMetrics.widthPixels;
        //  G.display_h = displayMetrics.heightPixels;
        //  G.scale = Math.max(G.display_w/1280.0f, G.display_h/800.0f);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                int h = mAdView.getLayoutParams().height;
                AdGoogleDisplaySupport.scale(mAdView, ratio);
                AdGoogleDisplaySupport.panelAdjust(mAdView, (int) (h * ratio));
                //  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            }
        });
        layout.addView(mAdView);
        return layout;
    }


    */


    /**
     * example 1 implementation of the switch view

    private BiAdAdapterSwitcher imple_switch_view(final UltimateRecyclerView rv) {
        final adap adp1 = new adap(createadmob(), new ArrayList<String>());
        final regular adp2 = new regular(new ArrayList<String>());
        final BiAdAdapterSwitcher switchable = new BiAdAdapterSwitcher(rv, adp2, adp1);
        return switchable;
    }
     */
    private Handler osh = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadmore);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ultimateRecyclerView = (UltimateRecyclerView) findViewById(R.id.ultimate_recycler_view);
        ultimateRecyclerView.setHasFixedSize(false);
        linearLayoutManager = new LinearLayoutManager(this);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.setRecylerViewBackgroundColor(Color.parseColor("#ffffff"));


        /**
         *  example 2 implementation enhancement of list view

        bi_sw = imple_switch_view(ultimateRecyclerView)
                .onEnableRefresh(100)
                .EnableAutoDisableLoadMoreByMaxPages()
                .onEnableLoadmore(R.layout.custom_bottom_progressbar, 2000, new BiAdAdapterSwitcher.onLoadMore() {
                    @Override
                    public boolean request_start(int current_page_no, int itemsCount, int maxLastVisiblePosition, final BiAdAdapterSwitcher bi, boolean refresh) {


                        osh.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                bi.load_more_data(SampleDataboxset.newList(5));
                                //test the max pages
                                bi.setMaxPages(10);
                            }
                        }, 2000);

                        return true;
                    }
                });
         */
        TextView b = (TextView) findViewById(R.id.del);
        b.setText("with Ad");
        TextView a = (TextView) findViewById(R.id.add);
        a.setText("with out Ad");
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 *  example 2 implementation enhancement of list view
                 *  without advertisement configurations
                 */
               // bi_sw.init(false);
            }

        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 *  example 2 implementation enhancement of list view
                 *  with advertisement configuration
                 */
             //   bi_sw.init(true);
            }
        });
        setup_spinner();
    }

    private void remove_all_items() {
        bi_sw.removeALL();
    }

    private void setup_spinner() {
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setPrompt("test functions");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        spinnerAdapter.add("noth");
        spinnerAdapter.add("remove all");
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  ultimateRecyclerView.setItemAnimator(Type.values()[position].getAnimator());
                //   ultimateRecyclerView.getItemAnimator().setAddDuration(300);
                //   ultimateRecyclerView.getItemAnimator().setRemoveDuration(300);
                if (position == 1) {
                    remove_all_items();
                    spinner.setSelection(0, true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}

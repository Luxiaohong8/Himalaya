package com.lzq.himalaya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.lzq.himalaya.adapter.IndicatorAdapter;
import com.lzq.himalaya.adapter.MainContentAdapter;
import com.lzq.himalaya.base.BaseActivity;
import com.lzq.himalaya.base.BaseFragment;
import com.lzq.himalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.category.Category;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity {
    private static final String TAG="MainActivity";
    private MagicIndicator mMagicIndicator;
    private ViewPager mContentPager;
    private IndicatorAdapter mIndicatorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    private void initEvent() {
        mIndicatorAdapter.setOnIndicatorTapClickListener(new IndicatorAdapter.OnIndicatorTapClickListener() {
            @Override
            public void onTabClick(int index) {
                if(mContentPager!=null){
                    mContentPager.setCurrentItem(index,false);
                }
            }
        });
    }

    private void initView(){
        mMagicIndicator = (MagicIndicator) findViewById(R.id.main_indicator);
        mMagicIndicator.setBackgroundColor(this.getResources().getColor(R.color.main_color));

        //创建适配器
        mIndicatorAdapter=new IndicatorAdapter(this);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(mIndicatorAdapter);

        //viewpager
        mContentPager=(ViewPager)findViewById(R.id.content_pager);
        //创建内容适配器
        FragmentManager suppportFragmentManager=getSupportFragmentManager();
        MainContentAdapter mainContentAdapter=new MainContentAdapter(suppportFragmentManager);
        mContentPager.setAdapter(mainContentAdapter);

        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mContentPager);


    }
}

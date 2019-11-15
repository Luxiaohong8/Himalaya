package com.lzq.himalaya.fragments;

import android.content.Intent;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.lzq.himalaya.DetailActivity;
import com.lzq.himalaya.R;
import com.lzq.himalaya.adapter.AlbumListAdapter;
import com.lzq.himalaya.base.BaseFragment;
import com.lzq.himalaya.interfaces.IRecommendViewCallback;
import com.lzq.himalaya.presenters.AlbumDetailPresenter;
import com.lzq.himalaya.presenters.RecommendPresenter;
import com.lzq.himalaya.utils.Constants;
import com.lzq.himalaya.utils.LogUtil;
import com.lzq.himalaya.views.UILoader;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendFragment extends BaseFragment implements IRecommendViewCallback,UILoader.OnRetryClickListener,AlbumListAdapter.OnAlbumItemClickListener{
    private static final String TAG="RecommendFragment";
    private View mRootView;
    private RecyclerView mRecommendRv;
    private AlbumListAdapter mRecommendListAdapter;
    private RecommendPresenter mRecommendPresenter;
    private UILoader mUiLoader;

    @Override
    protected View onSubViewLoaded(final LayoutInflater layoutInflater, ViewGroup container) {
        mUiLoader=new UILoader(getContext()) {
            @Override
            protected View getSuccessView(ViewGroup container) {
                return createSuccessView(layoutInflater,container);
            }
        };
        mRecommendPresenter=RecommendPresenter.getInstance();
        mRecommendPresenter.registerViewCallback(this);
        mRecommendPresenter.getRecommendList();
        if (mUiLoader.getParent() instanceof ViewGroup) {
            ((ViewGroup) mUiLoader.getParent()).removeView(mUiLoader);
        }
        mUiLoader.setOnRetryClickListener(this);
        return mUiLoader;
    }

    private View createSuccessView(LayoutInflater layoutInflater, ViewGroup container) {
        //加载
        mRootView=layoutInflater.inflate(R.layout.fragment_recommend,container,false);
        //recyclerView使用
        mRecommendRv=mRootView.findViewById(R.id.recommend_list);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);

        mRecommendRv.setLayoutManager(layoutManager);
        mRecommendRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top= UIUtil.dip2px(view.getContext(),5);
                outRect.bottom=UIUtil.dip2px(view.getContext(),5);
                outRect.left = UIUtil.dip2px(view.getContext(), 5);
                outRect.right = UIUtil.dip2px(view.getContext(), 5);
            }
        });
        //拿数据
        mRecommendListAdapter=new AlbumListAdapter();
        mRecommendRv.setAdapter(mRecommendListAdapter);
        mRecommendListAdapter.setAlbumItemClickListener(this);
        return mRootView;
    }


    private void upRecommendUI(List<Album> albumList) {
        mRecommendListAdapter.setData(albumList);
    }

    @Override
    public void onRecommendListLoaded(List<Album> result) {
        LogUtil.d(TAG, "onRecommendListLoaded");
        mRecommendListAdapter.setData(result);
        mUiLoader.updateStatus(UILoader.UIStatus.SUCCESS);
    }

    @Override
    public void onNetworkError() {
        LogUtil.d(TAG, "onNetworkError");
        mUiLoader.updateStatus(UILoader.UIStatus.NETWORK_ERROR);
    }

    @Override
    public void onEmpty() {
        LogUtil.d(TAG, "onEmpty");
        mUiLoader.updateStatus(UILoader.UIStatus.EMPTY);
    }

    @Override
    public void onLoading() {
        LogUtil.d(TAG, "onLoading");
        mUiLoader.updateStatus(UILoader.UIStatus.LOADING);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //取消接口的注册
        if (mRecommendPresenter != null) {
            mRecommendPresenter.unRegisterViewCallback(this);
        }
    }


    @Override
    public void onRetryClick() {
        if(mRecommendPresenter!=null){
            mRecommendPresenter.getRecommendList();
        }
    }

    @Override
    public void onItemClick(int position, Album album) {
        AlbumDetailPresenter.getInstance().setTargetAlbum(album);
        Intent intent = new Intent(getContext(), DetailActivity.class);
        startActivity(intent);
    }
}

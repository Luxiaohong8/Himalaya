package com.lzq.himalaya;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lzq.himalaya.base.BaseActivity;
import com.lzq.himalaya.interfaces.IAlbumDetailViewCallback;
import com.lzq.himalaya.presenters.AlbumDetailPresenter;
import com.lzq.himalaya.utils.ImageBlur;
import com.lzq.himalaya.utils.LogUtil;
import com.lzq.himalaya.views.RoundRectImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;

public class DetailActivity extends BaseActivity implements IAlbumDetailViewCallback {
    private static final String TAG = "DetailActivity";
    private ImageView mLargeCover;
    private RoundRectImageView mSmallCover;
    private TextView mAlbumTitle;
    private TextView mAlbumAuthor;
    private AlbumDetailPresenter mAlbumDetailPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        initView();
        initPresenter();

    }

    private void initView() {
        mLargeCover = this.findViewById(R.id.iv_large_cover);
        mSmallCover = this.findViewById(R.id.viv_small_cover);
        mAlbumTitle = this.findViewById(R.id.tv_album_title);
        mAlbumAuthor = this.findViewById(R.id.tv_album_author);
    }

    private void initPresenter() {
        //这个是专辑详情的presenter.
        mAlbumDetailPresenter = AlbumDetailPresenter.getInstance();
        mAlbumDetailPresenter.registerViewCallback(this);
    }

    @Override
    public void onDetailListLoaded(List<Track> tracks) {

    }

    @Override
    public void onAlbumLoaded(Album album) {
        if (mAlbumTitle != null) {
            mAlbumTitle.setText(album.getAlbumTitle());
        }

        if (mAlbumAuthor != null) {
            mAlbumAuthor.setText(album.getAnnouncer().getNickname());
        }

        //做毛玻璃效果
        if (mLargeCover != null && null != mLargeCover) {
            Picasso.with(this).load(album.getCoverUrlLarge()).into(mLargeCover, new Callback() {
                @Override
                public void onSuccess() {
                    Drawable drawable = mLargeCover.getDrawable();
                    if (drawable != null) {
                        //到这里才说明是有图片的
                        ImageBlur.makeBlur(mLargeCover, DetailActivity.this);
                    }
                }

                @Override
                public void onError() {
                    LogUtil.d(TAG, "onError");
                }
            });

        }

        if (mSmallCover != null) {
            Picasso.with(this).load(album.getCoverUrlLarge()).into(mSmallCover);
        }
    }
}

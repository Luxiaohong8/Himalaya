package com.lzq.himalaya.interfaces;

import com.lzq.himalaya.base.IBasePresenter;

public interface IAlbumDetailPresenter extends IBasePresenter<IAlbumDetailViewCallback> {
    /**
     * 下拉刷新更多内容
     */
    void pull2RefreshMore();

    /**
     * 上接加载更多
     */
    void loadMore();


    /**
     * 获取专辑详情
     *
     * @param albumId
     * @param page
     */
    void getAlbumDetail(int albumId, int page);

}

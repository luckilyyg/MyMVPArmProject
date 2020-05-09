package com.gy.wanandroid.mvp.presenter;

import android.app.Application;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gy.wanandroid.app.utils.RxUtils;
import com.gy.wanandroid.mvp.http.BaseHttpBean;
import com.gy.wanandroid.mvp.http.entity.ArticleBean;
import com.gy.wanandroid.mvp.ui.adapter.ArticleAdapter;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.gy.wanandroid.mvp.contract.HomeContract;

import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/08/2020 15:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    List<ArticleBean.DatasBean> articleBean;
    @Inject
    ArticleAdapter mAdapter;
    private boolean isFresh = true;
    private int pageIndex = 0;
    private int PAGE_SIZE=15;

    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView) {
        super(model, rootView);
    }

    public void getArticle(int page) {
        pageIndex = page;
        mModel.getArticleInfo(page)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseHttpBean<ArticleBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseHttpBean<ArticleBean> articleBeanBaseHttpBean) {
                        if (articleBeanBaseHttpBean.isSuccess()) {
                            ArticleBean articleBeans = articleBeanBaseHttpBean.getData();
                            int size = articleBean.size();
                            if (isFresh) {
                                articleBean = articleBeans.getDatas();
                                mAdapter.replaceData(articleBean);
                                mRootView.getArticleInfo(articleBean);
                            } else {
                                if (size > 0) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (size < PAGE_SIZE) {
                                                //第一页如果不够一页就不显示没有更多数据布局(比如只有十三条数据)
                                                mAdapter.loadMoreEnd(isFresh);

                                            } else {
                                                mAdapter.loadMoreComplete();
                                            }
                                            articleBean.addAll(articleBeans.getDatas());
                                            mAdapter.addData(articleBeans.getDatas());
                                            mRootView.getArticleInfo(articleBean);
                                        }
                                    }, 1000);

                                }

                            }
                        } else {
                            mRootView.showMessage(articleBeanBaseHttpBean.getErrorMsg());
                        }
                    }
                });
    }


    public void autoData() {
        isFresh = true;
        pageIndex = 0;
        getArticle(pageIndex);

    }

    public void loadData() {
        isFresh = false;
        pageIndex++;
        getArticle(pageIndex);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}

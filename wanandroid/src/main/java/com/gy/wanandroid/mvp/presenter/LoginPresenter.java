package com.gy.wanandroid.mvp.presenter;

import android.app.Application;
import android.content.Intent;

import com.gy.wanandroid.app.utils.RxUtils;
import com.gy.wanandroid.mvp.http.BaseHttpBean;
import com.gy.wanandroid.mvp.http.entity.UserBean;
import com.gy.wanandroid.mvp.ui.activity.MainActivity;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.gy.wanandroid.mvp.contract.LoginContract;

@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
        super(model, rootView);
    }

    public void login(String phone, String password) {
        mModel.login(phone, password)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseHttpBean<UserBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseHttpBean<UserBean> userBeanBaseHttpBean) {
                        if (userBeanBaseHttpBean.isSuccess()) {
                            mRootView.loginResult(userBeanBaseHttpBean.getData());
                        } else {
                            mRootView.showMessage(userBeanBaseHttpBean.getErrorMsg());
                        }
                    }
                });
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

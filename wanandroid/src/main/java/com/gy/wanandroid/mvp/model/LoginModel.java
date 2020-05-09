package com.gy.wanandroid.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.gy.wanandroid.mvp.http.BaseHttpBean;
import com.gy.wanandroid.mvp.http.api.service.LoginService;
import com.gy.wanandroid.mvp.http.entity.UserBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.gy.wanandroid.mvp.contract.LoginContract;

import io.reactivex.Observable;


@ActivityScope
public class LoginModel extends BaseModel implements LoginContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public LoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseHttpBean<UserBean>> login(String phone, String password) {
        return mRepositoryManager.obtainRetrofitService(LoginService.class).loginByUsernamePassword(phone, password);
    }
}
package com.gy.wanandroid.mvp.contract;

import com.gy.wanandroid.mvp.http.BaseHttpBean;
import com.gy.wanandroid.mvp.http.entity.UserBean;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import io.reactivex.Observable;

public interface LoginContract {

    interface View extends IView {
        void loginResult(UserBean msg);

    }


    interface Model extends IModel {
        Observable<BaseHttpBean<UserBean>> login(String phone, String password);
    }
}

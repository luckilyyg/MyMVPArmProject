package com.gy.wanandroid.mvp.http.api.service;

import com.gy.wanandroid.mvp.http.BaseHttpBean;
import com.gy.wanandroid.mvp.http.entity.UserBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created on 2020/5/8 13:38
 *
 * @auther super果
 * @annotation
 */
public interface LoginService {

    @FormUrlEncoded
    @POST("/user/login")
    Observable<BaseHttpBean<UserBean>> loginByUsernamePassword(@Field("username") String username, @Field("password") String password);
}

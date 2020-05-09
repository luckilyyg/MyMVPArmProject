package com.gy.wanandroid.mvp.http.api.service;

import com.gy.wanandroid.mvp.http.BaseHttpBean;
import com.gy.wanandroid.mvp.http.entity.ArticleBean;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created on 2020/5/8 16:00
 *
 * @auther super果
 * @annotation
 */
public interface ArticleService {
    /**
     * 首页文章列表
     * http://www.wanandroid.com/article/list/0/json
     * /article/list/{page}/json
     */
    @GET("article/list/{page}/json/")
    Observable<BaseHttpBean<ArticleBean>> getArticle(@Path("page") int page);
}

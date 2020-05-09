package com.gy.wanandroid.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.gy.wanandroid.di.module.SystemModule;
import com.gy.wanandroid.mvp.contract.SystemContract;

import com.jess.arms.di.scope.FragmentScope;
import com.gy.wanandroid.mvp.ui.fragment.SystemFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/08/2020 15:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = SystemModule.class, dependencies = AppComponent.class)
public interface SystemComponent {
    void inject(SystemFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SystemComponent.Builder view(SystemContract.View view);

        SystemComponent.Builder appComponent(AppComponent appComponent);

        SystemComponent build();
    }
}
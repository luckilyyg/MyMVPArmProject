package com.gy.wanandroid.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gy.wanandroid.R;
import com.gy.wanandroid.di.component.DaggerLoginComponent;
import com.gy.wanandroid.mvp.contract.LoginContract;
import com.gy.wanandroid.mvp.http.entity.UserBean;
import com.gy.wanandroid.mvp.presenter.LoginPresenter;
import com.gy.wanandroid.mvp.view.DialogText;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/08/2020 13:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.editText2)
    EditText editText2;
    @BindView(R.id.button)
    Button button;
    private DialogText mDialog;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mDialog = new DialogText(LoginActivity.this, R.style.MyDialog);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        showInfo(message, 0);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
        finish();
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void loginResult(UserBean msg) {
        showInfo("登录成功", 1);
    }


    @OnClick(R.id.button)
    public void onViewClicked() {
        if (editText.getText().toString().isEmpty() || editText2.getText().toString().isEmpty()) {
            mDialog.show();
            showInfo("手机号或密码不能为空", 0);
        } else {
            mDialog.show();
            mDialog.setText("正在登录...", "");
            if (mPresenter != null) mPresenter.login(editText.getText().toString(), editText2.getText().toString());
        }

    }

    /**
     * @param errorString
     * @param is
     */
    public void showInfo(final String errorString, final int is) {
        String isString = "";
        if (is == 1) {
            isString = "success";
        }
        if (is == 0) {
            isString = "fail";
        }
        if (mDialog != null) {
            mDialog.setText(errorString, isString);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mDialog.dismiss();
                    if (is == 1) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                }
            }, 1000);
        }
    }


}

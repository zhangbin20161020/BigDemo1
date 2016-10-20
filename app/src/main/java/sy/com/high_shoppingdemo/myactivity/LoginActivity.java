package sy.com.high_shoppingdemo.myactivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import sy.com.high_shoppingdemo.R;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences preferences;

    //AppKey   17ef7a5e15200
    Toolbar login_toolBar;
    ImageView qq_imageViw, weixin_imageView, sinaWeibo_imageView, qqZone_imageView;
    TextInputEditText username_editText, password_editText;
    TextInputLayout userName_textInputLayout, password_textInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 必须要写  初始化  ShareSDK
        ShareSDK.initSDK(this);

        //查找控件
        login_toolBar = (Toolbar) findViewById(R.id.login_toolBar);
        userName_textInputLayout = (TextInputLayout) findViewById(R.id.userName_textInputLayout);
        password_textInputLayout = (TextInputLayout) findViewById(R.id.password_textInputLayout);
        username_editText = (TextInputEditText) findViewById(R.id.username_editText);
        password_editText = (TextInputEditText) findViewById(R.id.password_editText);
        qq_imageViw = (ImageView) findViewById(R.id.qq_imageView);
        weixin_imageView = (ImageView) findViewById(R.id.weixin_imageView);
        sinaWeibo_imageView = (ImageView) findViewById(R.id.sinaWeibo_imageView);
        qqZone_imageView = (ImageView) findViewById(R.id.qqZone_imageView);

        //ToolBar
        login_toolBar.setTitle("登录");
        login_toolBar.setBackgroundColor(Color.DKGRAY);
        login_toolBar.setNavigationIcon(android.R.drawable.ic_menu_manage);
        login_toolBar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(login_toolBar);

        //分别给qq  微信   新浪微博  QQ空间设置点击事件
        qqClick();
        weixinClick();
        sinaWeiboClick();
        qqZoneClick();
        //用户名文本变化监听
        username_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String userName = s.toString();
                if (userName.length() > 15) {
                    userName_textInputLayout.setError("输入错误,请输入正确的用户名");
                }else {
                    userName_textInputLayout.setError("");

                }
            }
        });
        //用户名密码文本变化监听
        password_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String pass = s.toString();
                if (pass.length() > 15) {
                    password_textInputLayout.setError("密码输入错误~~~");
                }else {
                    password_textInputLayout.setError("");
                }
            }
        });

    }


    private void qqZoneClick() {
        qqZone_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTypeName(QZone.NAME);
            }
        });
    }

    private void sinaWeiboClick() {
        sinaWeibo_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTypeName(SinaWeibo.NAME);
            }
        });
    }

    private void weixinClick() {
        weixin_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTypeName(Wechat.NAME);
            }
        });
    }

    private void qqClick() {
        qq_imageViw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTypeName(QQ.NAME);
            }
        });
    }

    //登录 注册  按钮点击事件
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_login:
                break;
            case R.id.login_register:
                break;
        }

    }


    private void getTypeName(String name) {
        //获取平台
        Platform platform = ShareSDK.getPlatform(this, name);
        //移除账号的信息缓存(一般用于退出登录的时候清除账号的所有缓存信息)
        platform.removeAccount();
        //展示用户账号信息
        platform.showUser(null);
        //监听事件
        platform.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                //登陸成功   若不用Looper  则打印异常信息
//                Looper.prepare();
//                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
//                Looper.loop();

                //通过平台数据库得到   用户Id  Name  Icon
                String userId = platform.getDb().getUserId();
                String userName = platform.getDb().getUserName();
                String userIcon = platform.getDb().getUserIcon();

                //共享参数存储数据
                preferences = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("icon", userIcon);
                editor.putString("name", userName);
       //         editor.putInt("flag",1);
                editor.commit();
                //判断结果码
                setResult(RESULT_OK);

                Log.e("SY", "_______userId_____" + userId);
                Log.e("SY", "______userName______" + userName);
                Log.e("SY", "________userIcon____" + userIcon);
                finish();

                  /*  //新浪
                   String name = (String) hashMap.get("name");
                    String headImgUrl = (String) hashMap.get("avatar_hd");
                   Log.e("SY", "===name===" + name);
                   Log.e("SY", "===headImgUrl===" + headImgUrl);*/

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                //打印错误信息
                Log.e("SY", "____onError  错误信息______" + throwable.getMessage());
            }

            @Override
            public void onCancel(Platform platform, int i) {
                //取消
                Log.e("SY", "____onCancel   取消登录______");
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //ToolBar上左上角返回键  操作
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

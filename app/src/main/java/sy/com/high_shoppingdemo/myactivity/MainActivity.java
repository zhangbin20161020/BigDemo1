package sy.com.high_shoppingdemo.myactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sy.com.high_shoppingdemo.R;
import sy.com.high_shoppingdemo.adapter.FirstListViewAdapter;
import sy.com.high_shoppingdemo.adapter.ShouYeAdapter;
import sy.com.high_shoppingdemo.apiservice.GetShouYe;
import sy.com.high_shoppingdemo.bean.ShouYeBean;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    CircleImageView imageView;
    TextView textView;
    private SharedPreferences preferences;
    private String icon, name;
    //判断是否是 登录状态,默认未登录
    boolean isLogin = false;
    String[] arr = {"裙子", "上衣", "外套", "裤子", "鞋", "包", "美妆", "配饰", "男士"};
    List<ShouYeBean.DataBean.ItemsBean.ComponentBean.ItemsBeans> list = new ArrayList<>();
    int pre;
    @BindView(R.id.shouye_listView)
    ListView shouye_listView;
    @BindView(R.id.shouye_gridView)
    GridView shouye_gridView;
    ShouYeAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//设置ToolBar
        initToolBar();

        //获取 SharedPreferences对象
        preferences = getSharedPreferences("login", MODE_PRIVATE);

        //获得并实例化NavigationView头布局
        setHeader();

        //点击NavigationView 每条Item
        clickNavigationViewItem();


        //第0 个元素     头布局   点击头像跳到登录页面
        navigationView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(intent, 100);
            }
        });


        ButterKnife.bind(this);
        shouye_listView.setAdapter(new FirstListViewAdapter(arr, this));
        //默认选中ListView第一条Item
        shouye_listView.post(new Runnable() {
            @Override
            public void run() {
                shouye_listView.getChildAt(0).setBackgroundColor(Color.BLUE);
            }
        });

        //http://api-v2.mall.hichao.com/category/list?ga=%2Fcategory%2Flist

        initgridView(0);

        adapter = new ShouYeAdapter(list, MainActivity.this);
        shouye_gridView.setAdapter(adapter);
    }

    //填充首页 GridView
    private void initgridView(final int position) {
        String baseUrl = "http://api-v2.mall.hichao.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetShouYe getShouYe = retrofit.create(GetShouYe.class);
        Call<ShouYeBean> shouYeList = getShouYe.getShouYeList();
        shouYeList.enqueue(new Callback<ShouYeBean>() {
            @Override
            public void onResponse(Call<ShouYeBean> call, Response<ShouYeBean> response) {
                List<ShouYeBean.DataBean.ItemsBean.ComponentBean.ItemsBeans> itemsBeen = response.body().getData().getItems().get(position).getComponent().getItems();
                list.clear();
                //添加数据
                list.addAll(itemsBeen);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<ShouYeBean> call, Throwable t) {

            }
        });
    }

    private void initToolBar() {
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolBar);

        //设置ToolBar颜色  标题  背景
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("首页");
        toolbar.setNavigationIcon(android.R.drawable.star_off);
        toolbar.setBackgroundColor(Color.DKGRAY);
        setSupportActionBar(toolbar);

        //万能开关
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        //状态  同步
        toggle.syncState();

        //给ToolBar上菜单按钮   设置监听事件
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_delete:
                        break;
                }
                return false;
            }
        });
    }

    //菜单点击事件
    private void clickNavigationViewItem() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.shoye:
                        toolbar.setTitle("首页");
                        break;
                    case R.id.shoucang:
                        toolbar.setTitle("收藏");
                        break;
                    case R.id.shopping:
                        toolbar.setTitle("购物车");
                        break;
                    case R.id.exit:
                        //退出状态时   设置原有
                        Toast.makeText(MainActivity.this, "退出登录", Toast.LENGTH_SHORT).show();
                        imageView.setImageResource(R.mipmap.share_favorite);
                        textView.setText("未登录");
                        break;
                }
                //将当前item 设置为选中状态
                item.setChecked(true);
                drawerLayout.closeDrawer(Gravity.LEFT);
                return true;
            }
        });
    }

    private void setHeader() {
        View view = navigationView.getHeaderView(0);
        imageView = (CircleImageView) view.findViewById(R.id.header_imageView);
        textView = (TextView) view.findViewById(R.id.header_textView);
    }


    //菜单返回键  监听抽屉状态
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            //判断抽屉打开  关闭状态
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    drawerLayout.openDrawer(Gravity.LEFT);
                    toolbar.setTitle("购物车");
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //enable为true时，菜单添加图标有效，enable为false时无效。4.0系统之后默认无效
        setIconEnable(menu, true);
        //加载菜单文件
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        //搜索数据
        searchData(menu);
        //分享数据
        shareData(menu);
        return super.onCreateOptionsMenu(menu);
    }


    //搜索数据
    private void searchData(Menu menu) {
        //得到搜索  item项
        MenuItem item = menu.findItem(R.id.menu_search);

        //根据指定item得到   View视图
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        //设置文本变化监听事件
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(MainActivity.this, newText, Toast.LENGTH_SHORT).show();

                return false;
            }
        });

    }

    //分享数据
    private void shareData(Menu menu) {
        //查找 item项
        MenuItem item = menu.findItem(R.id.menu_share);
        //根据指定item  得到ShareActionProvider
        ShareActionProvider provider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setDataAndType(Uri.parse(""), "text/*");

        //设置分享意图 目的 动作
        provider.setShareIntent(intent);
    }

    //enable为true时，菜单添加图标有效，enable为false时无效。4.0系统之后默认无效
    private void setIconEnable(Menu menu, boolean b) {
        if (menu != null) {

            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {

                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, b);
                } catch (Exception e) {
                }
            }
        }
    }

    //判断请求码与结果码   利用SharedPreferences取出用户图像和名称
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                login();

            }
        }
    }

    private void login() {
        if (!isLogin) {
            icon = preferences.getString("icon", "");
            name = preferences.getString("name", "");
            //登录成功  设置数据
            Picasso.with(MainActivity.this).load(icon).into(imageView);
            textView.setText(name);
            isLogin = true;
        } else {
            imageView.setImageResource(R.mipmap.share_favorite);
            textView.setText("未登录");
        }
    }


    @OnItemClick(R.id.shouye_listView)
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        shouye_listView.getChildAt(pre).setBackgroundColor(Color.WHITE);
        shouye_listView.getChildAt(position).setBackgroundColor(Color.BLUE);
        pre = position;
        initgridView(position);


    }

    @OnItemClick(R.id.shouye_gridView)
    public void gridViewDetail(AdapterView<?> adapterView, View view, int position, long l){
        Intent intent = new Intent(MainActivity.this,GridViewDetailActivity.class);
        String word = list.get(position).getComponent().getWord();
        intent.putExtra("word",word);
        startActivity(intent);
    }

}

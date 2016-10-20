package sy.com.high_shoppingdemo.myactivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sy.com.high_shoppingdemo.R;
import sy.com.high_shoppingdemo.adapter.GridViewDetailAdapter;
import sy.com.high_shoppingdemo.apiservice.GetGridDetailData;
import sy.com.high_shoppingdemo.bean.ShouYe_DetailBean;
import sy.com.high_shoppingdemo.callback.OnItemClickListener;

public class GridViewDetailActivity extends AppCompatActivity {

    //http://api-v2.mall.hichao.com/search/skus?query=连衣裙%20%20&sort=all&ga=%252Fsearch%252Fskus&flag=&cat=&asc=1
    @BindView(R.id.detail_toolBar)
    Toolbar detail_toolBar;
    @BindView(R.id.detail_recyclerView)
    RecyclerView detail_recyclerView;
    List<ShouYe_DetailBean.DataBean.ItemsBean> list = new ArrayList<>();
    GridViewDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view_detail);

        ButterKnife.bind(this);
        //设置ToolBar颜色  标题  背景
        initToolBar();

        //得到MainActivity传过来的商品名称名字
        Intent intent = getIntent();
        String word = intent.getStringExtra("word");

        //填充RecyclerView
        setDetailRecyclerView();
//利用网络框架 Retrofit  得到数据
        getRetrofitData(word);

    }

    private void getRetrofitData(String word) {
        String baseUrl = "http://api-v2.mall.hichao.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetGridDetailData getGridDetailData = retrofit.create(GetGridDetailData.class);
        Call<ShouYe_DetailBean> detailList = getGridDetailData.getDetailList(word + "%20%20&sort=all&ga=%252Fsearch%252Fskus&flag=&cat=&asc=1");
        detailList.enqueue(new Callback<ShouYe_DetailBean>() {
            @Override
            public void onResponse(Call<ShouYe_DetailBean> call, Response<ShouYe_DetailBean> response) {
                int size = response.body().getData().getItems().size();
                for (int i = 0; i < size; i++) {
                    List<ShouYe_DetailBean.DataBean.ItemsBean> items = response.body().getData().getItems();
                    list.addAll(items);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ShouYe_DetailBean> call, Throwable t) {

            }
        });
    }


    private void setDetailRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        detail_recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new GridViewDetailAdapter(list, this);
        detail_recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void setOnItemClickListener(int position) {
                String sourceId = list.get(position).getComponent().getAction().getSourceId();
                Intent intent = new Intent(GridViewDetailActivity.this, Third_WebViewActivity.class);
                intent.putExtra("sourceId", sourceId);
                startActivity(intent);
            }
        });
    }

    private void initToolBar() {
        detail_toolBar.setTitleTextColor(Color.WHITE);
        detail_toolBar.setTitle("列表详情");
        detail_toolBar.setNavigationIcon(android.R.drawable.star_off);
        detail_toolBar.setBackgroundColor(Color.DKGRAY);
        setSupportActionBar(detail_toolBar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

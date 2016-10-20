package sy.com.high_shoppingdemo.apiservice;

import retrofit2.Call;
import retrofit2.http.GET;
import sy.com.high_shoppingdemo.bean.ShouYeBean;

/**
 * Created by Administrator on 2016/10/18.
 */
public interface GetShouYe {
    //word : 连衣裙
   // picUrl : http://s0.mingxingyichu.cn/group6/M00/3C/F6/wKgBjVdVmi2AbDLSAAA17pibmfg144.jpg
    @GET(value = "category/list?ga=%2Fcategory%2Flist")
    Call<ShouYeBean> getShouYeList();
}

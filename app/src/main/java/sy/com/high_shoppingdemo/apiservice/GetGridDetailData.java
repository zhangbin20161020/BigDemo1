package sy.com.high_shoppingdemo.apiservice;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import sy.com.high_shoppingdemo.bean.ShouYe_DetailBean;

/**
 * Created by Administrator on 2016/10/19.
 */
public interface GetGridDetailData {
//http://api-v2.mall.hichao.com/search/skus?query=连衣裙%20%20&sort=all&ga=%252Fsearch%252Fskus&flag=&cat=&asc=1
    @GET (value = "search/skus?")
    Call<ShouYe_DetailBean> getDetailList(@Query(value = "query") String query);
}

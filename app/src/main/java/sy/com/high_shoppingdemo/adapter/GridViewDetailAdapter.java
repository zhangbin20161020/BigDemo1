package sy.com.high_shoppingdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sy.com.high_shoppingdemo.R;
import sy.com.high_shoppingdemo.bean.ShouYe_DetailBean;
import sy.com.high_shoppingdemo.callback.OnItemClickListener;

/**
 * Created by Administrator on 2016/10/19.
 */
public class GridViewDetailAdapter extends RecyclerView.Adapter<GridViewDetailAdapter.MyDetailViewHolder> {
    List<ShouYe_DetailBean.DataBean.ItemsBean> list;
    Context context;
    LayoutInflater inflater;
    OnItemClickListener onItemClickListener;


    public GridViewDetailAdapter(List<ShouYe_DetailBean.DataBean.ItemsBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = inflater.inflate(R.layout.detail_gridview_item_layout,parent,false);
        MyDetailViewHolder viewHolder = new MyDetailViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyDetailViewHolder holder, final int position) {

        holder.detail_description.setText(list.get(position).getComponent().getDescription());
        holder.detail_price.setText("$"+list.get(position).getComponent().getPrice());
        holder.detail_originPrice.setText("原$"+list.get(position).getComponent().getOrigin_price());
        holder.detail_sales.setText("销量:"+list.get(position).getComponent().getSales());

        Picasso.with(context).load(list.get(position).getComponent().getPicUrl()).into(holder.detail_imageView);
    //短按事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null){
                    onItemClickListener.setOnItemClickListener(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyDetailViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.detail_imageView)
        ImageView detail_imageView;
        @BindView(R.id.detail_description)
        TextView detail_description;
        @BindView(R.id.detail_price)
        TextView detail_price;
        @BindView(R.id.detail_originPrice)
        TextView detail_originPrice;
        @BindView(R.id.detail_sales)
        TextView detail_sales;

        public MyDetailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

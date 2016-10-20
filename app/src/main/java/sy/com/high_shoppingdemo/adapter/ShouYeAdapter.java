package sy.com.high_shoppingdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sy.com.high_shoppingdemo.R;
import sy.com.high_shoppingdemo.bean.ShouYeBean;

/**
 * Created by Administrator on 2016/10/18.
 */
public class ShouYeAdapter extends BaseAdapter {

   List<ShouYeBean.DataBean.ItemsBean.ComponentBean.ItemsBeans> list;
    Context context;
    LayoutInflater inflater;


    public ShouYeAdapter(List<ShouYeBean.DataBean.ItemsBean.ComponentBean.ItemsBeans> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.shouye_grid_layout,parent,false);
           //绑定
            ButterKnife.bind(holder,convertView);

            convertView.setTag(holder);
        }else {
           holder = (ViewHolder) convertView.getTag();
        }

        ShouYeBean.DataBean.ItemsBean.ComponentBean.ItemsBeans beans = list.get(position);
        holder.shouye_textView.setText(beans.getComponent().getWord());

        Picasso.with(context).load(beans.getComponent().getPicUrl()).into(holder.shouye_imageView);

        return convertView;
    }

    class ViewHolder{
        @BindView(R.id.shouye_imageView)
        ImageView shouye_imageView;
        @BindView(R.id.shouye_textView)
        TextView shouye_textView;
    }
}

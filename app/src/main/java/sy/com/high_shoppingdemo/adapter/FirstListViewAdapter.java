package sy.com.high_shoppingdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sy.com.high_shoppingdemo.R;

/**
 * Created by Administrator on 2016/10/19.
 */
public class FirstListViewAdapter extends BaseAdapter {
    String[] arr;
    Context context;
    LayoutInflater inflater;


    public FirstListViewAdapter(String[] arr, Context context) {
        this.arr = arr;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arr.length;
    }

    @Override
    public Object getItem(int position) {
        return arr[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.shouye_listview_item,parent,false);
            ButterKnife.bind(viewHolder,convertView);

            convertView.setTag(viewHolder);
        }else {
           viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.list_textView.setText(arr[position]);
        return convertView;
    }

    class ViewHolder{
        @BindView(R.id.list_textView)
        TextView list_textView;
    }
}

package commontestware.com.delieveryordermgmt.adapter;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import commontestware.com.delieveryordermgmt.R;
import commontestware.com.delieveryordermgmt.model.ModelDineIn;


public class CustomAdapter extends BaseAdapter {

    /* public String bill_foodrate[];
     public String bill_foodamount[];
     public String bill_foodquantity[];
     public int bill_rate[];
     public int bill_quantity[];
     public int bill_amt[];*/
    public Activity context;
    public LayoutInflater inflater;
    ArrayList<ModelDineIn> list = new ArrayList<>();
    int total = 0;

    public CustomAdapter(Activity context, ArrayList<ModelDineIn> listmodel) {
        super();
        this.context = context;
        this.list = listmodel;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView bill_foodrate;
        //TextView bill_rate;
        TextView bill_quantity;
        TextView bill_amt;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.bill_list_items, null);
            holder.bill_foodrate = (TextView) convertView.findViewById(R.id.bill_foodrate);
            //holder.bill_rate = (TextView) convertView.findViewById(R.id.bill_rate);
            holder.bill_quantity = (TextView) convertView.findViewById(R.id.bill_quantity);
            holder.bill_amt = (TextView) convertView.findViewById(R.id.bill_amt);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // holder.bill_rate.setText(list.get(position).getRate());
        holder.bill_quantity.setText(list.get(position).getQuantity());
        holder.bill_amt.setText(list.get(position).getTotalamount());
        holder.bill_foodrate.setText(list.get(position).getCategory());

        return convertView;
    }
}
package commontestware.com.delieveryordermgmt.adapter;


import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import commontestware.com.delieveryordermgmt.R;
import commontestware.com.delieveryordermgmt.model.ModelDineIn;
import commontestware.com.delieveryordermgmt.model.ModelTakeAway;

/**
 * Created by BLT0006 on 2/18/2016.
 */
public class CustomAdapterAway extends BaseAdapter {


    /*public String bill_foodrate[];
    public String bill_foodamount[];
    public String bill_foodquantity[];
    public int bill_rate[];
    public int bill_quantity[];
    public int bill_amt[];*/
    public Activity context;
    public LayoutInflater inflater;
    ArrayList<ModelTakeAway> list = new ArrayList<>();


    public CustomAdapterAway(Activity context, ArrayList<ModelTakeAway> listmodel) {
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
       // TextView delivery;
        //TextView assign;
       // TextView mobile;
        //TextView delivery_edt;
        //TextView delivery_name;
        //TextView delivery_number;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.bill_list_takeaway, null);
            holder.bill_foodrate = (TextView) convertView.findViewById(R.id.bill_foodrate);
           // holder.bill_rate = (TextView) convertView.findViewById(R.id.bill_rate);
            holder.bill_quantity = (TextView) convertView.findViewById(R.id.bill_quantity);
            holder.bill_amt = (TextView) convertView.findViewById(R.id.bill_amt);
            //holder.foodlist = (TextView) convertView.findViewById(R.id.edit_food_item);
           // holder.foodquantity = (TextView) convertView.findViewById(R.id.edit_quantity);
            //holder.foodrate = (TextView) convertView.findViewById(R.id.edit_rate);
           // holder.totalamount = (TextView) convertView.findViewById(R.id.edit_total_amt);
           // holder.delivery = (TextView) convertView.findViewById(R.id.edit_delivery);
           /* holder.assign = (TextView) convertView.findViewById(R.id.edit_assign);
            holder.mobile = (TextView) convertView.findViewById(R.id.edit_mobile);*/
            //holder.delivery_edt = (TextView) convertView.findViewById(R.id.delivery_edt);
            //holder.delivery_name = (TextView) convertView.findViewById(R.id.delivery_name);
            //holder.delivery_number = (TextView) convertView.findViewById(R.id.delivery_number);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //holder.bill_rate.setText(list.get(position).getFoodrate());
        holder.bill_quantity.setText(list.get(position).getFoodquantity());
        holder.bill_amt.setText(list.get(position).getTotalamount());
        holder.bill_foodrate.setText(list.get(position).getFoodlist());
        // holder.foodlist.setText(list.get(position).getFoodlist());
        //holder.foodquantity.setText(list.get(position).getFoodquantity());
        // holder.foodrate.setText(list.get(position).getFoodrate());
        //holder.totalamount.setText(list.get(position).getTotalamount());
        //holder.delivery.setText(list.get(position).getDelivery());
       /* holder.assign.setText(list.get(position).getAssign());
        holder.mobile.setText(list.get(position).getMobile());*/
        //holder.delivery_edt.setText(list.get(position).getDelivery_edt());
       // holder.delivery_name.setText(list.get(position).getDelivery_name());
       // holder.delivery_number.setText(list.get(position).getDelivery_number());

        return convertView;
    }
}

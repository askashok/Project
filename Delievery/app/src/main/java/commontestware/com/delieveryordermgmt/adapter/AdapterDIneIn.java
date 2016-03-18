package commontestware.com.delieveryordermgmt.adapter;

/**
 * Created by BLT0006 on 3/1/2016.
 */

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

public class AdapterDIneIn extends BaseAdapter {


    public Context context;
    public LayoutInflater inflater;
    ArrayList<ModelDineIn> list1 = new ArrayList<>();
    int total = 0;

    public AdapterDIneIn(Context context, ArrayList<ModelDineIn> listmodel) {
        super();
        this.context = context;
        this.list1 = listmodel;

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public int getCount() {
        return 0;
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
        TextView bill_quantity;
        TextView bill_amt;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_dinein, null);
            holder.bill_foodrate = (TextView) convertView.findViewById(R.id.bill_foodrate);
            holder.bill_quantity = (TextView) convertView.findViewById(R.id.bill_quantity);
            holder.bill_amt = (TextView) convertView.findViewById(R.id.bill_amt);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // holder.bill_rate.setText(list.get(position).getRate());
        holder.bill_quantity.setText(list1.get(position).getQuantity());
        holder.bill_amt.setText(list1.get(position).getTotalamount());
        holder.bill_foodrate.setText(list1.get(position).getCategory());

        return convertView;
    }
}

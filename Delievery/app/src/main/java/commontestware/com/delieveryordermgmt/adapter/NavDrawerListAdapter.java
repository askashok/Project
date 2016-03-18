package commontestware.com.delieveryordermgmt.adapter;

/**
 * Created by BLT0037 on 9/19/2015.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;

import commontestware.com.delieveryordermgmt.R;

public class NavDrawerListAdapter extends BaseAdapter {


    private Context context;
    String[] title_arr;
    int[] image_arr;
    ArrayList<String> count_ar;
    int selectedItem;
    Typeface helvetica_reg;
    String count_value;

    public NavDrawerListAdapter(Context context, String[] title, int[] navMenuimage_arr) {
        this.context = context;
        this.title_arr = title;
        this.image_arr = navMenuimage_arr;


        this.count_value = count_value;
    }


    @Override
    public int getCount() {
        return title_arr.length;
    }

    @Override
    public Object getItem(int position) {
        return title_arr[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_listitem, parent, false);
        }
        highlightItem(position, convertView);
        LinearLayout list_back = (LinearLayout) convertView
                .findViewById(R.id.main_item_linear);
//        RelativeLayout line_relative = (RelativeLayout)convertView.findViewById(R.id.line_relative);


        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        ImageView img = (ImageView) convertView.findViewById(R.id.listimg);
        txtTitle.setText(title_arr[position]);
        Log.v("title_arr", title_arr[position]);
        img.setImageResource(image_arr[position]);

        return convertView;

    }

    private void highlightItem(int position, View result) {
        if (position == selectedItem) {
// you can define your own color of selected item here
            result.setBackgroundColor(context.getResources().getColor(R.color.listpressed));
        } else {
// you can define your own default selector here
            result.setBackgroundColor(context.getResources().getColor(R.color.listdefault));
        }

    }

    public void selectedItemPosition(int position) {

        selectedItem = position;
        notifyDataSetChanged();
    }
}
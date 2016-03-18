package commontestware.com.delieveryordermgmt.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import commontestware.com.delieveryordermgmt.R;

/**
 * Created by BLT0006 on 2/25/2016.
 */
public class LoadMore extends Activity {

    private Context context = null;
    private ListView list = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_more);
        list = (ListView) findViewById(android.R.id.list);

        //code to set adapter to populate list
        View footerView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.load_more, null, false);
        list.addFooterView(footerView);
    }
}

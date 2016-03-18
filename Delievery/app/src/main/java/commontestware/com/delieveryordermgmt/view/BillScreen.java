package commontestware.com.delieveryordermgmt.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

import commontestware.com.delieveryordermgmt.R;
import commontestware.com.delieveryordermgmt.adapter.CustomAdapter;
import commontestware.com.delieveryordermgmt.model.ModelDineIn;

/**
 * Created by BLT0006 on 2/26/2016.
 */
public class BillScreen extends Activity {

    ArrayList<ModelDineIn> list = new ArrayList<>();
    CustomAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billscreen);

    }
}

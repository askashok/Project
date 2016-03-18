package commontestware.com.delieveryordermgmt.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

import commontestware.com.delieveryordermgmt.Utills.Constant;
import commontestware.com.delieveryordermgmt.Utills.Popup;
import commontestware.com.delieveryordermgmt.Utills.Popupaway;
import commontestware.com.delieveryordermgmt.adapter.CustomAdapter;
import commontestware.com.delieveryordermgmt.adapter.CustomAdapterAway;
import commontestware.com.delieveryordermgmt.model.ModelDineIn;
import commontestware.com.delieveryordermgmt.view.HomeScreen;
import commontestware.com.delieveryordermgmt.R;
import commontestware.com.delieveryordermgmt.view.Splash;
import commontestware.com.delieveryordermgmt.database.DbHelper;
import commontestware.com.delieveryordermgmt.model.ModelEmployee;
import commontestware.com.delieveryordermgmt.model.ModelTakeAway;
import commontestware.com.delieveryordermgmt.model.Modelone;
import commontestware.com.delieveryordermgmt.view.Utility;

/**
 * Created by BLT0037 on 9/19/2015.
 */
public class TakeAwayFragment extends android.support.v4.app.Fragment {
    Button btn_okay;
    ImageView img_plus;
    EditText edt_quantity, edt_delivery;
    TextView edt_total_amt, delivery_edt, delivery_name, delivery_number;
    ModelTakeAway modelTakeAway;
    DbHelper takehelper;
    int calculatedValue;
    int spinnerCatagory, spinnerrateitem, spinnerassign, spinnermobile = 0;
    ArrayList<ModelTakeAway> takeawayList = new ArrayList<>();
    ArrayList<Modelone> dine;
    ArrayList<ModelEmployee> assign;
    Spinner category, rateitems, edt_assign, edt_mobile;
    String taxValue, totValue, grossValue;
    String selbState = null;
    String selsState = null;
    String selssState = null;
    String selsssState = null;


    ListView listView1;
    CustomAdapterAway adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragementtakeaway, container, false);
        takehelper = new DbHelper(getActivity());
        getIdForEdttxt(rootView);
        loadSpinnerData();

        img_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (category.getSelectedItem().toString().equalsIgnoreCase("")) {
                    Constant.alert(getActivity(), "select Item in a list");
                    Log.e("Selected...1", category.getSelectedItem().toString());

                } else if (edt_quantity.getText().toString().equalsIgnoreCase("")) {
                    Constant.alert(getActivity(), "Enter Quantity");
                } else if (rateitems.getSelectedItem().toString().equalsIgnoreCase("")) {
                    Constant.alert(getActivity(), "select Item");
                    Log.e("Selected...2", category.getSelectedItem().toString());
                } else if (edt_total_amt.getText().toString().equalsIgnoreCase("")) {
                    Constant.alert(getActivity(), "Enter Quantity");
                } else if (edt_assign.getSelectedItem().toString().equalsIgnoreCase("")) {
                    Constant.alert(getActivity(), "select Item");
                } else if (edt_mobile.getSelectedItem().toString().equalsIgnoreCase("")) {
                    Constant.alert(getActivity(), "select Item");
                } else {
                    modelTakeAway = new ModelTakeAway();
                    modelTakeAway.setFoodlist(selbState);
                    modelTakeAway.setFoodquantity(edt_quantity.getText().toString());
                    modelTakeAway.setTotalamount(edt_total_amt.getText().toString());
                    modelTakeAway.setDelivery(edt_delivery.getText().toString());
                    modelTakeAway.setAssign(selssState);
                    modelTakeAway.setMobile(selsssState);
                    takeawayList.add(modelTakeAway);
                    adapter = new CustomAdapterAway(getActivity(), takeawayList);
                    listView1.setAdapter(adapter);


                    Utility.setListViewHeightBasedOnChildren(listView1);

                    Log.e("Modelarr size", "" + takeawayList.size());

                    cleartext();

                    Toast.makeText(getActivity(), "Item Inserted to Bill", Toast.LENGTH_SHORT).show();

                }
            }

        });

        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent send = new Intent(getActivity(), Popupaway.class);
                Log.e("Size in intent", "" + takeawayList.size());
                send.putExtra("yourlist", takeawayList);
                String value = edt_delivery.getText().toString();
                Log.e("delivery", "delivery " + value);
                send.putExtra("delivery", edt_delivery.getText().toString());
                String assignto = edt_assign.getSelectedItem().toString();
                send.putExtra("assignto", edt_assign.getSelectedItem().toString());
                String mobile = edt_mobile.getSelectedItem().toString();
                send.putExtra("mobile", edt_mobile.getSelectedItem().toString());
                startActivity(send);
                cleartext();

                ;


            }
        });

        edt_quantity.addTextChangedListener(quantityAmntWatcher);
        return rootView;

    }

    private final TextWatcher quantityAmntWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            calculate(rateitems.getSelectedItem().toString(), "" + s);

        }

        public void afterTextChanged(Editable s) {
        }
    };
    private final TextWatcher rateTextWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            calculate(edt_quantity.getText().toString(), "" + s);
        }

        public void afterTextChanged(Editable s) {
        }
    };

    public void calculate(String inp1, String inp2) {
        if (inp1.equals("")) {
            inp1 = "0";
        }
        if (inp2.equals("")) {
            inp2 = "0";
        }
        try {
            int value1 = Integer.parseInt(inp1);
            int value2 = Integer.parseInt(inp2);
            calculatedValue = (value1 * value2);
            edt_total_amt.setText(String.valueOf(calculatedValue));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cleartext() {

        edt_total_amt.setText("");
        edt_quantity.setText("");

    }


    private void getIdForEdttxt(View rootView) {
        category = (Spinner) rootView.findViewById(R.id.edit_food_item);
        edt_quantity = (EditText) rootView.findViewById(R.id.edit_quantity);
        rateitems = (Spinner) rootView.findViewById(R.id.edit_rate);
        edt_total_amt = (TextView) rootView.findViewById(R.id.edit_total_amt);
        edt_delivery = (EditText) rootView.findViewById(R.id.edit_delivery);
        edt_assign = (Spinner) rootView.findViewById(R.id.edit_assign);
        edt_mobile = (Spinner) rootView.findViewById(R.id.edit_mobile);
        delivery_name = (TextView) rootView.findViewById(R.id.delivery_name);
        listView1 = (ListView) rootView.findViewById(R.id.list_view1);
        delivery_number = (TextView) rootView.findViewById(R.id.delivery_number);
        btn_okay = (Button) rootView.findViewById(R.id.btn_ok);
        img_plus = (ImageView) rootView.findViewById(R.id.img_plus);
    }

    private void loadSpinnerData() {
        DbHelper db = new DbHelper(getActivity());

        ArrayList<String> name = new ArrayList<>();
        dine = db.getspinner();
        for (Modelone model : dine) {
            name.add(model.getName());
        }
        ArrayList<String> rate = new ArrayList<>();
        for (Modelone model : dine) {
            rate.add(model.getRate());
        }
        ArrayList<String> assignto = new ArrayList<>();
        assign = db.getemployee();
        for (ModelEmployee model : assign) {
            assignto.add(model.getEmpname());
        }
        ArrayList<String> mobile = new ArrayList<>();
        assign = db.getemployee();
        for (ModelEmployee model : assign) {
            mobile.add(model.getEmpmobile());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinneractivity, name);
        ArrayAdapter<String> rateadapter = new ArrayAdapter<String>(getActivity(), R.layout.spinneractivity, rate);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinneractivity, assignto);
        ArrayAdapter<String> mobileAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinneractivity, mobile);


        mobileAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        Adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        rateadapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);


        edt_assign.setAdapter(Adapter);
        edt_assign.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edt_mobile.setSelection(position);

                spinnerassign++;
                if (spinnerassign > 1) {

                    selssState = (String) edt_assign.getSelectedItem();

                    Toast.makeText(parent.getContext(), "You selected: " + selssState,
                            Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        edt_mobile.setAdapter(mobileAdapter);
        edt_mobile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edt_assign.setSelection(position);

                spinnermobile++;
                if (spinnermobile > 1) {
                    selsssState = (String) edt_mobile.getSelectedItem();

                    Toast.makeText(parent.getContext(), "You selected: " + selsssState,
                            Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        category.setAdapter(dataAdapter);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                rateitems.setSelection(position);

                spinnerCatagory++;
                if (spinnerCatagory > 1) {
                    selbState = (String) category.getSelectedItem();

                    Toast.makeText(parent.getContext(), "You selected: " + selbState,
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        rateitems.setAdapter(rateadapter);
        rateitems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> parent, View view,
                                                                           int position, long id) {
                                                    category.setSelection(position);

                                                    spinnerrateitem++;
                                                    if (spinnerrateitem > 1) {
                                                        selsState = (String) rateitems.getSelectedItem();

                                                        Toast.makeText(parent.getContext(), "You selected: " + selsState,
                                                                Toast.LENGTH_LONG).show();
                                                    }

                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> parent) {

                                                }
                                            }

        );

    }
}
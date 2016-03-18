package commontestware.com.delieveryordermgmt.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.internal.widget.TintImageView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;

import commontestware.com.delieveryordermgmt.R;
import commontestware.com.delieveryordermgmt.Utills.Constant;
import commontestware.com.delieveryordermgmt.Utills.Popup;
import commontestware.com.delieveryordermgmt.adapter.AdapterDIneIn;
import commontestware.com.delieveryordermgmt.adapter.CustomAdapter;
import commontestware.com.delieveryordermgmt.database.DbHelper;
import commontestware.com.delieveryordermgmt.model.ModelDineIn;
import commontestware.com.delieveryordermgmt.model.Modelone;
import commontestware.com.delieveryordermgmt.view.Utility;

/**
 * Created by BLT0037 on 9/19/2015.
 */
public class DineInFragment extends android.support.v4.app.Fragment {

    ImageView img_plus;
    Button btn_ok;
    EditText quantity;
    TextView total_amt, gross_amt;
    ModelDineIn modeldinein;
    DbHelper dinehelper;
    ArrayList<Modelone> dine;
    ArrayList<ModelDineIn> dineInList = new ArrayList<>();
    int calculatedValue;
    int spinnerCatagory, spinnerFoodItem, spinnerrateitem = 0;
    Spinner category, fooditem, rateitem;
    String selState = null;
    String selsState = null;
    String selssState = null;

    ListView listView;
    CustomAdapter adapter;
    Activity cont;
    ArrayList<ModelDineIn> list1 = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmentdinein, container, false);
        dinehelper = new DbHelper(getActivity());
        getIdForEditText(rootView);
        loadSpinnerData();

        return rootView;
    }

    private final TextWatcher taxWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            calculate_tax("" + s);
        }

        public void afterTextChanged(Editable s) {
        }
    };
    private final TextWatcher quantityAmntWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            calculate("" + s, rateitem.getSelectedItem().toString());

        }

        public void afterTextChanged(Editable s) {
        }
    };
    private final TextWatcher rateTextWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            calculate(quantity.getText().toString(), "" + s);
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
            total_amt.setText(String.valueOf(calculatedValue));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void calculate_tax(String taxAmnt) {
        if (taxAmnt.equals("")) {
            taxAmnt = "0";
        }

        float value3 = Float.parseFloat(taxAmnt);
        float taxcals = ((calculatedValue + value3 / 100 * calculatedValue));
        gross_amt.setText("" + taxcals);

    }

    public void cleartext() {
        quantity.setText("");
        total_amt.setText("");

    }

    private void getIdForEditText(View rootView) {
        category = (Spinner) rootView.findViewById(R.id.edt_category);
        fooditem = (Spinner) rootView.findViewById(R.id.edt_food_item);
        quantity = (EditText) rootView.findViewById(R.id.edt_quantity);
        rateitem = (Spinner) rootView.findViewById(R.id.edt_rate);
        total_amt = (TextView) rootView.findViewById(R.id.edt_totalamt);
        listView = (ListView) rootView.findViewById(R.id.list_view);
        btn_ok = (Button) rootView.findViewById(R.id.btn_ok);
        img_plus = (ImageView) rootView.findViewById(R.id.img_plus);


        img_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (category.getSelectedItem().toString().equalsIgnoreCase("")) {
                    Constant.alert(getActivity(), "select Item in a list");
                    Log.e("Selected...1", category.getSelectedItem().toString());

                } else if (fooditem.getSelectedItem().toString().equalsIgnoreCase("")) {
                    Constant.alert(getActivity(), "select Item");
                    Log.e("Selected...2", category.getSelectedItem().toString());
                } else if (quantity.getText().toString().equalsIgnoreCase("")) {
                    Constant.alert(getActivity(), "Enter Quantity");
                } else if (rateitem.getSelectedItem().toString().equalsIgnoreCase("")) {
                    Constant.alert(getActivity(), "select Item");
                } else {
                    modeldinein = new ModelDineIn();
                    modeldinein.setCategory(category.getSelectedItem().toString());
                    modeldinein.setQuantity(quantity.getText().toString());
                    modeldinein.setTotalamount(total_amt.getText().toString());
                    dineInList.add(modeldinein);
                    adapter = new CustomAdapter(getActivity(), dineInList);
                    listView.setAdapter(adapter);

                    Utility.setListViewHeightBasedOnChildren(listView);


                    Log.e("Modelarr size", "" + dineInList.size());

                    cleartext();
                    Toast.makeText(getActivity(), "Item Inserted to Bill", Toast.LENGTH_SHORT).show();

                }
            }

        });


        btn_ok.setOnClickListener(new View.OnClickListener()

                                  {

                                      @Override
                                      public void onClick(View v) {

                                          Intent intent = new Intent(getActivity(), Popup.class);
                                          Log.e("Size in intent", "" + dineInList.size());
                                          intent.putExtra("yourlist", dineInList);
                                          startActivity(intent);
                                          cleartext();
                                      }
                                  }
        );


        quantity.addTextChangedListener(quantityAmntWatcher);
        Utility.setListViewHeightBasedOnChildren(listView);
    }

    private void loadSpinnerData() {

        DbHelper db = new DbHelper(getActivity());

        ArrayList<String> name = new ArrayList<>();
        dine = db.getspinner();
        for (Modelone model : dine) {
            name.add(model.getName());
        }
        ArrayList<String> food = new ArrayList<>();
        for (Modelone model : dine) {
            food.add(model.getFoodtype());
        }
        ArrayList<String> rate = new ArrayList<>();
        for (Modelone model : dine) {
            rate.add(model.getRate());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinneractivity, name);
        ArrayAdapter<String> foodadapter = new ArrayAdapter<String>(getActivity(), R.layout.spinneractivity, food);
        ArrayAdapter<String> rateadapter = new ArrayAdapter<String>(getActivity(), R.layout.spinneractivity, rate);

        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        foodadapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        rateadapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);


        category.setAdapter(dataAdapter);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                fooditem.setSelection(position);
                rateitem.setSelection(position);
                spinnerCatagory++;
                if (spinnerCatagory > 1) {
                    selState = (String) category.getSelectedItem();

                    Toast.makeText(parent.getContext(), "You selected: " + selState,
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fooditem.setAdapter(foodadapter);
        fooditem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category.setSelection(position);
                rateitem.setSelection(position);
                spinnerFoodItem++;
                if (spinnerFoodItem > 1) {
                    selsState = (String) fooditem.getSelectedItem();

                    Toast.makeText(parent.getContext(), "You selected: " + selsState,
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        rateitem.setAdapter(rateadapter);
        rateitem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category.setSelection(position);
                fooditem.setSelection(position);
                spinnerrateitem++;
                if (spinnerrateitem > 1) {
                    selssState = (String) rateitem.getSelectedItem();


                    Toast.makeText(parent.getContext(), "You selected: " + selssState,
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}

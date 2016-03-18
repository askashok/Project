package commontestware.com.delieveryordermgmt.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import commontestware.com.delieveryordermgmt.R;
import commontestware.com.delieveryordermgmt.Utills.Constant;
import commontestware.com.delieveryordermgmt.database.DbHelper;
import commontestware.com.delieveryordermgmt.forgetpassword.ForgetPassword;
import commontestware.com.delieveryordermgmt.prefstorage.Pref_Storage;

public class MainActivity extends Activity {


    String username, password;
    Button registerin, signin;
    EditText editname, editpass;
    TextView forgetpassword;


    boolean doubleBackToExitPressedOnce = false;
    DbHelper data = new DbHelper(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getId();
        Intent intent = getIntent();
        String str = intent.getStringExtra("email");
        editname.setText(str);

        Intent send = getIntent();
        String strg = intent.getStringExtra("password");
        editpass.setText(strg);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForgetPassword.class);
                startActivity(intent);

            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editname.getText().toString();
                password = editpass.getText().toString();

                //assert emailValidator(username) : "Email Not valid  ";
                assert emailValidator("prabakar@gmail.com") : " Not valid";
               /* int value = 11;
                assert value>=18:" Not valid";*/

                if (editname.getText().toString().equalsIgnoreCase("")) {
                    Constant.alert(MainActivity.this, "Enter your username");
                } else if (!emailValidator(editname.getText().toString())) {

                    Constant.alert(MainActivity.this, "E-mail address is invalid");

                } else if (editpass.getText().toString().equalsIgnoreCase("")) {
                    Constant.alert(MainActivity.this, "Enter your password ");
                } else {

                    boolean getStatus = data.login(username, password);
                    if (getStatus) {

                        Pref_Storage.setUserDetails(MainActivity.this, username, editpass.getText().toString(), true);
                        Log.v("Name", username);
                        Intent intent = new Intent(MainActivity.this, HomeScreen.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                        finish();

                    } else {
                        Constant.alert(MainActivity.this, "Please enter correct Password!");
                    }
                }
            }
        });

        registerin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent(MainActivity.this, RegisterPage.class);
                startActivity(send);


            }
        });
    }

    private void getId() {
        registerin = (Button) findViewById(R.id.btn_register);
        signin = (Button) findViewById(R.id.btn_signin);
        editname = (EditText) findViewById(R.id.edt_name);
        editpass = (EditText) findViewById(R.id.edt_pass);
        forgetpassword = (TextView) findViewById(R.id.forgot);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

   /* @Override
    protected void onResume() {
        super.onResume();
        editname.setText(Pref_Storage.getDetail(MainActivity.this, "email"));
        editpass.setText(Pref_Storage.getDetail(MainActivity.this, "pass"));

    }*/

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
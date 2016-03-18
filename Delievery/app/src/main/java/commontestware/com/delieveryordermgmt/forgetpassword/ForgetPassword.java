package commontestware.com.delieveryordermgmt.forgetpassword;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import commontestware.com.delieveryordermgmt.R;
import commontestware.com.delieveryordermgmt.database.DbHelper;

/**
 * Created by BLT0008 on 9/22/2015.
 */
public class ForgetPassword extends Activity {
    EditText mail_ID;
    ImageView imageView;
    Button button;
    DbHelper dataBaseHandler;
    String Password;
    public static final String LOGCAT = ForgetPassword.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        dataBaseHandler = new DbHelper(this);
        mail_ID = (EditText) findViewById(R.id.edit_GetMailID);
        button = (Button) findViewById(R.id.button_GetMailID);
        setSvgImage();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mail_ID.getText().toString().equalsIgnoreCase("")) {
                    mail_ID.setError("Incorrect Email ID");
                } else if (!isEditTextContainEmail(mail_ID)) {
                    mail_ID.setError("Enter Valid Email ID");
                } else if (dataBaseHandler.register(mail_ID.getText().toString())) {
                    Password = dataBaseHandler.getPassword(mail_ID.getText().toString());
                    Log.d(LOGCAT, "Password Fetched");

                    SendCrashErrorMail(mail_ID.getText().toString(), Password);
                    Toast.makeText(getApplicationContext(), "Password Sent Successfully", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    private void setSvgImage() {
        Utilities.setImageDrawable(ForgetPassword.this, R.raw.mail, imageView);
    }

    public static boolean isEditTextContainEmail(EditText editText) {

        try {
            Pattern pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher matcher = pattern.matcher(editText.getText().toString());
            return matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void SendCrashErrorMail(final String mail_ID, final String password) {

        Thread mail_thread = new Thread() {

            @Override
            public void run() {
                Log.d(LOGCAT, "Mail Id" + mail_ID);
                Log.d(LOGCAT, "Password" + Password);

                Mail m = new Mail("vijayakumar.blisslogix@gmail.com", "VIJAYda90");
                String[] toArr = {mail_ID};
                m.setTo(toArr);
                m.setFrom("vijayakumar.blisslogix@gmail.com");
                m.setSubject("Password Recovery - Delivery order management");
                m.setBody(password);


                try {
                    if (m.send()) {
                    } else {
                    }
                } catch (Exception e) {
                    Log.e("MailApp", "Could not send email");
                    e.printStackTrace();
                }
            }

        };

        mail_thread.start();
    }
}

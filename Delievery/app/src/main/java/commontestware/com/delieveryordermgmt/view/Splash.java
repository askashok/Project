package commontestware.com.delieveryordermgmt.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

import commontestware.com.delieveryordermgmt.R;
import commontestware.com.delieveryordermgmt.prefstorage.Pref_Storage;

public class Splash extends Activity {
    long Delay = 1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splashscreen);
        Timer RunSplash = new Timer();
        TimerTask ShowSplash = new TimerTask() {
            @Override
            public void run() {
                if (Pref_Storage.getLoginState(Splash.this)) {
                    String userName = Pref_Storage.getUserDetails(Splash.this).get(0);
                    startActivity(new Intent(Splash.this, HomeScreen.class).putExtra("username", userName));

                    finish();
                } else {
                    startActivity(new Intent(Splash.this, MainActivity.class));
                    finish();
                }


            }
        };
        RunSplash.schedule(ShowSplash, Delay);
    }
}

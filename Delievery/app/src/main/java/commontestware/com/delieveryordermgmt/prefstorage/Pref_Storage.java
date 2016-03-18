package commontestware.com.delieveryordermgmt.prefstorage;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by BLT0037 on 9/26/2015.
 */
public class Pref_Storage {

    public static String MY_PREFS_NAME = "PrefName";

    private static SharedPreferences sharedPreferences = null;

    public static void openPref(Context context) {
        try {
            sharedPreferences = context.getSharedPreferences("Entry000123", Context.MODE_PRIVATE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void deleteKey(Context context, String key) {
        HashMap<String, String> result = new HashMap<String, String>();

        Pref_Storage.openPref(context);
        for (Map.Entry<String, ?> entry : Pref_Storage.sharedPreferences.getAll().entrySet()) {
            result.put(entry.getKey(), (String) entry.getValue());
        }

        boolean b = result.containsKey(key);
        if (b) {
            Pref_Storage.openPref(context);
            SharedPreferences.Editor prefsPrivateEditor = Pref_Storage.sharedPreferences.edit();
            prefsPrivateEditor.remove(key);

            prefsPrivateEditor.commit();
            prefsPrivateEditor = null;
            Pref_Storage.sharedPreferences = null;
        }
    }

    public static void setDetail(Context context, String key, String value) {
        try {
            Pref_Storage.openPref(context);
            SharedPreferences.Editor prefsPrivateEditor = Pref_Storage.sharedPreferences.edit();
            prefsPrivateEditor.putString(key, value);
            prefsPrivateEditor.commit();
            prefsPrivateEditor = null;
            Pref_Storage.sharedPreferences = null;
        } catch (Exception e) {

        }

    }

    public static Boolean checkDetail(Context context, String key) {
        HashMap<String, String> result = new HashMap<String, String>();

        Pref_Storage.openPref(context);
        for (Map.Entry<String, ?> entry : Pref_Storage.sharedPreferences.getAll().entrySet()) {
            result.put(entry.getKey(), (String) entry.getValue());
        }

        boolean b = result.containsKey(key);
        return b;
    }

    public static String getDetail(Context context, String key) {
        HashMap<String, String> result = new HashMap<String, String>();

        Pref_Storage.openPref(context);
        for (Map.Entry<String, ?> entry : Pref_Storage.sharedPreferences.getAll().entrySet()) {
            result.put(entry.getKey(), (String) entry.getValue());
        }

        String b = result.get(key);
        return b;

    }

    public static void setUserDetails(Context context, String uName, String pwd, boolean loginState) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString("UserName", uName);
        editor.putString("Pwd", pwd);
        editor.putBoolean("LoginState", loginState);
        editor.apply();
    }

    public static ArrayList<String> getUserDetails(Context context) {
        ArrayList<String> details = new ArrayList<>();
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        details.add(prefs.getString("UserName", ""));
        details.add(prefs.getString("Pwd", ""));
        //details.add(prefs.getString("UserName",""));
        return details;
    }

    public static boolean getLoginState(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean("LoginState", false);
    }
}

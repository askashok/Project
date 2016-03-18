package commontestware.com.delieveryordermgmt.Utills;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import commontestware.com.delieveryordermgmt.view.MainActivity;

/**
 * Created by BLT0037 on 10/22/2015.
 */
public class Constant {

    public static void alert(Context context, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(
                context);
        builder.setMessage(message);
       /* builder.setTitle("Alert");*/
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,
                                int which) {
                Log.e("info", "OK");
            }
        });
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Log.e("info", "Cancel");
                    }
                });
        builder.show();
    }


    public static void alertWithOutButtons(Context context, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(
                context);
        /*builder.setTitle("Alert");*/
        builder.setMessage(message);

        builder.show();
    }
}

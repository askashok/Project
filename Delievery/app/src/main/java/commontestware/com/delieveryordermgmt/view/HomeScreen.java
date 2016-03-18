package commontestware.com.delieveryordermgmt.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import commontestware.com.delieveryordermgmt.R;
import commontestware.com.delieveryordermgmt.Utills.Constant;
import commontestware.com.delieveryordermgmt.adapter.NavDrawerListAdapter;
import commontestware.com.delieveryordermgmt.database.DbHelper;
import commontestware.com.delieveryordermgmt.fragments.CategoryFragment;
import commontestware.com.delieveryordermgmt.fragments.DineInFragment;
import commontestware.com.delieveryordermgmt.fragments.EmployeeFragment;
import commontestware.com.delieveryordermgmt.fragments.TakeAwayFragment;
import commontestware.com.delieveryordermgmt.model.Model;
import commontestware.com.delieveryordermgmt.prefstorage.Pref_Storage;

/**
 * Created by BLT0037 on 9/19/2015.
 */
public class HomeScreen extends FragmentActivity
        implements View.OnClickListener, AdapterView.OnItemClickListener {


    boolean doubleBackToExitPressedOnce = false;

    public static final int CAMERA_CAPTURE_IMAGE_REGUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final String IMAGE_DIRECTORY_NAME = "Vijay Camera";
    static Uri fileUri;
    static String img;
    private static int RESULT_LOAD_IMAGE = 1;
    int onStartCount = 0;
    private static int NUM = 0;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private NavDrawerListAdapter nav_adapter;
    private String[] navMenuTitles_arr;
    private int[] navMenuimage_arr = new int[]{R.drawable.category, R.drawable.dinein, R.drawable.dineout, R.drawable.employee, R.drawable.logout};
    RelativeLayout menu, relativedrawer, relativeimg, toolbar;
    TextView txt_title, circle_txt;
    commontestware.com.delieveryordermgmt.roundedimage.RoundedImageView circleimg;
    ImageView btn_menu, image;
    String imageview;
    DbHelper dbhelper;
    Model model;
    String email, name, type, rate;
    String uName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
        dbhelper = new DbHelper(HomeScreen.this);
        uName = getIntent().getStringExtra("username");
        circleimg = (commontestware.com.delieveryordermgmt.roundedimage.RoundedImageView) findViewById(R.id.circleimg);
        circleimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

                AlertDialog.Builder builder = new AlertDialog.Builder(HomeScreen.this);
                builder.setTitle("Add Photo!");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            captureImage();
                        } else if (options[item].equals("Choose from Gallery")) {

                            Intent i = new Intent(
                                    Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                            startActivityForResult(i, 1);
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });
        navMenuTitles_arr = getResources().getStringArray(R.array.nav_drawer_items);
        getId();

        setButtonclick();
        if (savedInstanceState == null) {
            displayView(0);
        }
    }

    private void setButtonclick() {
        btn_menu.setOnClickListener(this);
        mDrawerList.setOnItemClickListener(this);
    }

    private void getId() {


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_relative);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        txt_title = (TextView) findViewById(R.id.txt_title);
        circle_txt = (TextView) findViewById(R.id.circletxt);
        btn_menu = (ImageView) findViewById(R.id.btn_menu);
        relativeimg = (RelativeLayout) findViewById(R.id.relativeimg);
        relativedrawer = (RelativeLayout) findViewById(R.id.relative_navigtion);
        toolbar = (RelativeLayout) findViewById(R.id.lineartoolbar);
        image = (ImageView) findViewById(R.id.imgplus);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.Fragment myFragment = getSupportFragmentManager().findFragmentByTag("CategoryFragment");
                if (myFragment != null && myFragment.isVisible() && myFragment instanceof CategoryFragment) {
                    Intent send = new Intent(HomeScreen.this, CreateCategory.class);
                    send.putExtra("add", "add");
                    startActivity(send);
                } else {
                    Intent send = new Intent(HomeScreen.this, Employee.class);
                    startActivity(send);
                }

            }
        });


        nav_adapter = new NavDrawerListAdapter(getApplicationContext(), navMenuTitles_arr, navMenuimage_arr);
        mDrawerList.setAdapter(nav_adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (onStartCount > 1) {
            this.overridePendingTransition(R.anim.anim_slide_in_right,
                    R.anim.anim_slide_out_right);

        } else if (onStartCount == 1) {
            onStartCount++;
        }
    }

    public void onBackPressed() {
       /* if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit the Application", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 6000);*/
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_layout);
        dialog.setCancelable(false);

        TextView textView = (TextView) dialog.findViewById(R.id.text_edit_or_delete);
        Button yes = (Button) dialog.findViewById(R.id.button_yes);
        Button no = (Button) dialog.findViewById(R.id.button_no);

        textView.setText("Do you want to logout?");

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent logout = new Intent(HomeScreen.this, Splash.class);
                startActivity(logout);
                finish();
            }
        });
        dialog.show();

    }

    @Override
    public void onClick(View v) {

        // TODO Auto-generated method stub
        switch (v.getId()) {

            case R.id.btn_menu:

                // TODO Auto-generated method stub
                mDrawerLayout.setVisibility(View.VISIBLE);
                boolean drawerOpen = mDrawerLayout.isDrawerOpen(relativedrawer);

                System.out.println("<<<<Draweropen>>>>" + drawerOpen);
                if (drawerOpen) {
                    mDrawerLayout.closeDrawer(relativedrawer);
                } else {
                    mDrawerLayout.openDrawer(relativedrawer);

                }
                break;
            default:
                break;

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            uName = getIntent().getStringExtra("username");
            String imagepath = dbhelper.getProfileData(Pref_Storage.getUserDetails(HomeScreen.this).get(0)).get(1);
            String name = dbhelper.getProfileData(Pref_Storage.getUserDetails(HomeScreen.this).get(0)).get(0);

            // circle_txt.setText(Pref_Storage.getDetail(HomeScreen.this, "email"));
            Log.e("imagepath", "" + imagepath + "name" + name);
            circle_txt.setText(name);
            if (imagepath != null) {
                if (!imagepath.equals("")) {
                    circleimg.setImageBitmap(setImageIntoImageView(imagepath));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap setImageIntoImageView(String imagePath) {
        Bitmap bm = null;

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            final Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
            Matrix matrix = new Matrix();
            matrix.postRotate(getImageOrientation(imagePath));
            Bitmap rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bm = Bitmap.createScaledBitmap(rotateBitmap, 150, 150, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        displayView(position);
        nav_adapter.selectedItemPosition(position);
    }

    public void displayView(int position) {

        Fragment fragment = null;
        android.support.v4.app.FragmentManager fragmentManager = (FragmentManager) getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (position) {
            case 0:
                NUM = 0;
                System.out.println("selected num...0" + NUM);
                txt_title.setText(navMenuTitles_arr[position]);
                mDrawerLayout.closeDrawer(relativedrawer);
                image.setVisibility(View.VISIBLE);
                fragment = new CategoryFragment();
                fragmentTransaction.replace(R.id.frame_container, fragment, "CategoryFragment");

                break;
            case 1:
                NUM = 1;
                txt_title.setText(navMenuTitles_arr[position]);
                fragment = new DineInFragment();
                mDrawerLayout.closeDrawer(relativedrawer);
                image.setVisibility(View.INVISIBLE);
                fragmentTransaction.replace(R.id.frame_container, fragment, "DineInFragment");

                break;
            case 2:
                NUM = 2;
                txt_title.setText(navMenuTitles_arr[position]);
                fragment = new TakeAwayFragment();
                mDrawerLayout.closeDrawer(relativedrawer);
                image.setVisibility(View.INVISIBLE);
                fragmentTransaction.replace(R.id.frame_container, fragment, "TakeAwayFragment");

                break;
            case 3:
                NUM = 3;
                System.out.println("selected num...3" + NUM);
                txt_title.setText(navMenuTitles_arr[position]);
                fragment = new EmployeeFragment();
                mDrawerLayout.closeDrawer(relativedrawer);
                image.setVisibility(View.VISIBLE);

                fragmentTransaction.replace(R.id.frame_container, fragment, "EmployeeFragment");


                break;
            case 4:
                NUM = 4;
                System.out.println("selected num...4" + NUM);
                Pref_Storage.setUserDetails(HomeScreen.this, "", "", false);
                txt_title.setText(navMenuTitles_arr[position]);
                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_dialog_layout);
                dialog.setCancelable(false);

                TextView textView = (TextView) dialog.findViewById(R.id.text_edit_or_delete);
                Button yes = (Button) dialog.findViewById(R.id.button_yes);
                Button no = (Button) dialog.findViewById(R.id.button_no);

                textView.setText("Do you want to logout?");

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent logout = new Intent(HomeScreen.this, Splash.class);
                        startActivity(logout);
                        finish();
                    }
                });
                dialog.show();
                break;
            default:
                break;
        }
        if (fragment != null) {
            fragmentTransaction.commit();
        }
    }

    public void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, 0);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fileUri = savedInstanceState.getParcelable("file_uri");
    }


    @Override
    protected void onActivityResult(int Reguestcode, int resultcode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(Reguestcode, resultcode, data);
        if (Reguestcode == 0) {
            if (resultcode == RESULT_OK) {

                previewCapturedImage();
            } else if (resultcode == RESULT_CANCELED) {

                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {

                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
        if (Reguestcode == 1) {
            if (resultcode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                //path
                imageview = cursor.getString(columnIndex);
                dbhelper.saveImagepath(imageview, uName);
                cursor.close();
                System.out.println("" + imageview);
                //resized image to fix the image view
                BitmapFactory.Options option = new BitmapFactory.Options();
                option.inSampleSize = 8;
                Bitmap bitmap = BitmapFactory.decodeFile(imageview, option);
                Matrix matrix = new Matrix();
                matrix.postRotate(getImageOrientation(imageview));
                Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);


                Bitmap resized = Bitmap.createScaledBitmap(rotatedBitmap, 150, 150, true);

                circleimg.setImageBitmap(resized);

            }
        }
    }

    public void previewCapturedImage() {
        // TODO Auto-generated method stub
        try {

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
            Log.v("Imagepathvion", fileUri.getPath() + "" + imageview);
            dbhelper.saveImagepath(fileUri.getPath(), uName);
            Matrix matrix = new Matrix();
            matrix.postRotate(getImageOrientation(fileUri.getPath()));
            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);


            Bitmap resized = Bitmap.createScaledBitmap(rotatedBitmap, 150, 150, true);

            circleimg.setImageBitmap(resized);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public static int getImageOrientation(String imagePath) {
        int rotate = 0;
        try {

            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(
                    imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate;
    }

    private Uri getOutputMediaFileUri(int type) {
        // TODO Auto-generated method stub
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private File getOutputMediaFile(int type) {
        // TODO Auto-generated method stub
        File mediastoredir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);
        if (!mediastoredir.exists()) {
            if (!mediastoredir.mkdir()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new java.util.Date());
        File mediafile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediafile = new File(mediastoredir.getPath() + File.separator
                    + "IMG_" + timestamp + ".jpg");
            System.out.println(" photo path " + mediastoredir.getPath()
                    + File.separator + "IMG_" + timestamp + ".jpg");
// image path
            img = mediastoredir.getPath() + File.separator + "IMG_"
                    + timestamp + ".jpg";

            System.out.println(" ### IMAGE" + image);
        } else {
            return null;
        }
        return mediafile;
    }


}

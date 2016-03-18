package commontestware.com.delieveryordermgmt.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import commontestware.com.delieveryordermgmt.R;
import commontestware.com.delieveryordermgmt.Utills.Constant;
import commontestware.com.delieveryordermgmt.database.DbHelper;
import commontestware.com.delieveryordermgmt.model.Model;


public class RegisterPage extends Activity {
    public static final int CAMERA_CAPTURE_IMAGE_REGUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final String IMAGE_DIRECTORY_NAME = "Vijay Camera";
    Uri fileUri = null;
    String img = null;

    private static int RESULT_LOAD_IMAGE = 1;

    Button signup, cancel;
    EditText name, email, password, confirmpassword, profile;
    CheckBox gender;
    ImageView add_image, edit;
    String image;
    Model model;
    DbHelper sql = new DbHelper(RegisterPage.this);
    commontestware.com.delieveryordermgmt.roundedimage.RoundedImageView photoimage;
    String sex = "female";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_register);
        getID();

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        photoimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterPage.this);
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

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equalsIgnoreCase("")) {
                    name.requestFocus();

                    Constant.alert(RegisterPage.this, "Enter your name");

                } else if (email.getText().toString().equalsIgnoreCase("")) {
                    email.requestFocus();
                    Constant.alert(RegisterPage.this, "Enter email address");

                } else if (!isValidEmail(email.getText().toString())) {

                    Constant.alert(RegisterPage.this, "E-mail address is invalid");

                } else if (password.getText().toString().equalsIgnoreCase("")) {
                    password.requestFocus();

                    Constant.alert(RegisterPage.this, "Enter password");

                } else if (confirmpassword.getText().toString().equalsIgnoreCase("")) {
                    confirmpassword.requestFocus();

                    Constant.alert(RegisterPage.this, "Enter conform password");

                } else if (!confirmpassword.getText().toString().equalsIgnoreCase(password.getText().toString())) {

                    Constant.alert(RegisterPage.this, "password does not match");

                } else if (sql.register(email.getText().toString())) {

                    Constant.alert(RegisterPage.this, "Registration Already Exists");

                }/* else if (fileUri == null && image == null) {
                    Constant.alert(RegisterPage.this, "Add profile Image");

                    //|| img == null|| img.equals("")

                }*/ else {

                    model = new Model();
                    model.setName(name.getText().toString());
                    model.setMail(email.getText().toString());
                    model.setPassword(password.getText().toString());
                    model.setConfirmpassword(confirmpassword.getText().toString());
                    model.setProfile(image);

                    sql.insert(model);

                    Constant.alertWithOutButtons(RegisterPage.this, "Succesfully Registered");

                    signup.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            cleartext();
                            finish();
                        }
                    }, 2000);
                    Intent send = new Intent(RegisterPage.this, MainActivity.class);
                    send.putExtra("email", email.getText().toString());
                    send.putExtra("password", confirmpassword.getText().toString());
                    startActivity(send);

                }
            }
        });
    }

    private void getID() {
        signup = (Button) findViewById(R.id.btn_signup);
        name = (EditText) findViewById(R.id.edt_name);
        email = (EditText) findViewById(R.id.edt_email);
        password = (EditText) findViewById(R.id.edt_passwrd);
        confirmpassword = (EditText) findViewById(R.id.edt_conirmpasswrd);
        cancel = (Button) findViewById(R.id.btn_cancel);
        photoimage = (commontestware.com.delieveryordermgmt.roundedimage.RoundedImageView) findViewById(R.id.photoimage);

    }

    public void cleartext() {

        name.setText("");
        email.setText("");
        password.setText("");
        confirmpassword.setText("");


    }

    public boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    private Bitmap getBitmap(String image) {
        Bitmap resized = null;

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            final Bitmap bitmap = BitmapFactory.decodeFile(image, options);


            Matrix matrix = new Matrix();
            matrix.postRotate(getImageOrientation(image));
            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);


            resized = Bitmap.createScaledBitmap(rotatedBitmap, 150, 150, true);

            photoimage.setImageBitmap(resized);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return resized;

    }

    public void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        image = null;
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
                fileUri = null;
                image = cursor.getString(columnIndex);
                cursor.close();
                Log.v("imagepath ", image);
                System.out.println("" + image);
                //resized image to fix the image view
                BitmapFactory.Options option = new BitmapFactory.Options();
                option.inSampleSize = 8;
                Bitmap bitmap = BitmapFactory.decodeFile(image, option);
                Matrix matrix = new Matrix();
                matrix.postRotate(getImageOrientation(image));
                Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);


                Bitmap resized = Bitmap.createScaledBitmap(rotatedBitmap, 150, 150, true);

                photoimage.setImageBitmap(resized);
                // photo.setImageBitmap(BitmapFactory.decodeFile(image));

            }
        }
    }

    public void previewCapturedImage() {
        // TODO Auto-generated method stub
        try {

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);

            image = fileUri.getPath();
            Matrix matrix = new Matrix();
            matrix.postRotate(getImageOrientation(fileUri.getPath()));
            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);

            Bitmap resized = Bitmap.createScaledBitmap(rotatedBitmap, 150, 150, true);

            photoimage.setImageBitmap(resized);
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
            image = mediastoredir.getPath() + File.separator + "IMG_"

                    + timestamp + ".jpg";

            System.out.println(" ### IMAGE" + image);
        } else {
            return null;
        }
        return mediafile;
    }
}

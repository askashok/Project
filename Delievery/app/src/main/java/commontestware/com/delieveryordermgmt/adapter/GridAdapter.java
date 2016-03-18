package commontestware.com.delieveryordermgmt.adapter;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.wrapp.floatlabelededittext.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import commontestware.com.delieveryordermgmt.R;
import commontestware.com.delieveryordermgmt.Utills.CompressImage;
import commontestware.com.delieveryordermgmt.Utills.Constant;
import commontestware.com.delieveryordermgmt.database.DbHelper;
import commontestware.com.delieveryordermgmt.model.Modelone;
import commontestware.com.delieveryordermgmt.view.CreateCategory;

public class GridAdapter extends BaseAdapter {

    public static final int CAMERA_CAPTURE_IMAGE_REGUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final String IMAGE_DIRECTORY_NAME = "Delivery Order Management";
    Uri fileUri = null;
    String img = null;
    private static int RESULT_LOAD_IMAGE = 1;

    private Context mContext;
    private Activity mActivity;
    DbHelper database;
    ArrayList<Modelone> category_list;
    LayoutInflater inflater;


    public GridAdapter(FragmentActivity activity, ArrayList<Modelone> mThumbIds) {
        this.mContext = activity;
        this.mActivity = activity;
        this.category_list = mThumbIds;
        inflater = LayoutInflater.from(activity);
        database = new DbHelper(mActivity);
    }

    @Override
    public int getCount() {
        return category_list.size();
    }

    @Override
    public Object getItem(int position) {
        return category_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView itemname;
        TextView categoryname;
        TextView categoryrate;
        ImageView edit, close;
        commontestware.com.delieveryordermgmt.roundedimage.RoundedImageView gridimage;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.griditems, null);
            holder.itemname = (TextView) convertView.findViewById(R.id.itemname);
            holder.categoryname = (TextView) convertView.findViewById(R.id.categoryname);
            holder.categoryrate = (TextView) convertView.findViewById(R.id.categoryrate);
            holder.gridimage = (commontestware.com.delieveryordermgmt.roundedimage.RoundedImageView) convertView.findViewById(R.id.Gridimage);
            holder.edit = (ImageView) convertView.findViewById(R.id.edit);
            holder.close = (ImageView) convertView.findViewById(R.id.close);

            holder.gridimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Add Photo!");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            if (options[item].equals("Take Photo")) {
                                // captureImage();
                            } else if (options[item].equals("Choose from Gallery")) {

                                Intent i = new Intent(
                                        Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                                // startActivityForResult(i, 1);
                            } else if (options[item].equals("Cancel")) {
                                dialog.dismiss();
                            }
                        }
                    });
                    builder.show();
                }
            });


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delAlert(mContext, "Do You Want to Delete", position);
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editAlert(mContext, "Do You Want to Edit", position);


            }
        });


        holder.categoryname.setText(category_list.get(position).getName());
        holder.itemname.setText(category_list.get(position).getFoodtype());
        holder.categoryrate.setText(category_list.get(position).getRate());
        try {
            if (!category_list.get(position).getImage_path().equalsIgnoreCase("")) {
                CompressImage compressImage = new CompressImage();
                Bitmap scaledBitmap = compressImage.getCompressImage(category_list.get(position).getImage_path());
                holder.gridimage.setImageBitmap(scaledBitmap);
                /*try {
                    Bitmap scaledBitmap = null;
                    BitmapFactory.Options options = new BitmapFactory.Options();

                    options.inJustDecodeBounds = true;
                    Bitmap bmp = BitmapFactory.decodeFile(category_list.get(position).getImage_path(), options);

                    int actualHeight = options.outHeight;
                    int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

                    float maxHeight = 816.0f;
                    float maxWidth = 612.0f;
                    float imgRatio = actualWidth / actualHeight;
                    float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

                    if (actualHeight > maxHeight || actualWidth > maxWidth) {
                        if (imgRatio < maxRatio) {               imgRatio = maxHeight / actualHeight;                actualWidth = (int) (imgRatio * actualWidth);               actualHeight = (int) maxHeight;             } else if (imgRatio > maxRatio) {
                            imgRatio = maxWidth / actualWidth;
                            actualHeight = (int) (imgRatio * actualHeight);
                            actualWidth = (int) maxWidth;
                        } else {
                            actualHeight = (int) maxHeight;
                            actualWidth = (int) maxWidth;

                        }
                    }

//      setting inSampleSize value allows to load a scaled down version of the original image

                    options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
                    options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
                    options.inPurgeable = true;
                    options.inInputShareable = true;
                    options.inTempStorage = new byte[16 * 1024];
                    try {
//          load the bitmap from its path
                        bmp = BitmapFactory.decodeFile(category_list.get(position).getImage_path(), options);
                    } catch (OutOfMemoryError exception) {
                        exception.printStackTrace();

                    }
                    try {
                        scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight,Bitmap.Config.ARGB_8888);
                    } catch (OutOfMemoryError exception) {
                        exception.printStackTrace();
                    }

                    float ratioX = actualWidth / (float) options.outWidth;
                    float ratioY = actualHeight / (float) options.outHeight;
                    float middleX = actualWidth / 2.0f;
                    float middleY = actualHeight / 2.0f;

                    Matrix scaleMatrix = new Matrix();
                    scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

                    Canvas canvas = new Canvas(scaledBitmap);
                    canvas.setMatrix(scaleMatrix);
                    canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
                    ExifInterface exif;
                    try {
                        exif = new ExifInterface(category_list.get(position).getImage_path());

                        int orientation = exif.getAttributeInt(
                                ExifInterface.TAG_ORIENTATION, 0);
                        Log.d("EXIF", "Exif: " + orientation);
                        Matrix matrix = new Matrix();
                        if (orientation == 6) {
                            matrix.postRotate(90);
                            Log.d("EXIF", "Exif: " + orientation);
                        } else if (orientation == 3) {
                            matrix.postRotate(180);
                            Log.d("EXIF", "Exif: " + orientation);
                        } else if (orientation == 8) {
                            matrix.postRotate(270);
                            Log.d("EXIF", "Exif: " + orientation);
                        }
                        scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                                scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                                true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    FileOutputStream out = null;
                    String filename = getFilename();
                    try {
                        out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
                        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
                        holder.gridimage.setImageBitmap(scaledBitmap);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }


                   *//* BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    final Bitmap bitmap = BitmapFactory.decodeFile(category_list.get(position).getImage_path(), options);


                    Matrix matrix = new Matrix();
                    matrix.postRotate(getImageOrientation(category_list.get(position).getImage_path()));
                    Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                            bitmap.getHeight(), matrix, true);


                    Bitmap resized = Bitmap.createScaledBitmap(rotatedBitmap, 150, 150, true);
*//*

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }*/

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = mContext.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public void delAlert(final Context context, String message, final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(
                context);
        builder.setMessage(message);
        builder.setTitle("Alert");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,
                                int which) {


                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                Log.e("info", "OK");
                boolean deleteOrNot = database.deleteContactdb(cutnull(category_list.get(position).getFoodtype()));
                if (deleteOrNot) {
                    category_list.remove(position);
                    notifyDataSetChanged();
                }
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

    public void editAlert(Context context, String message, final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(
                context);
        builder.setMessage(message);
        builder.setTitle("Alert");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,
                                int which) {
                Log.e("info", "OK");
                Intent i = new Intent(mContext, CreateCategory.class);
                i.putExtra("caregoryname", cutnull(category_list.get(position).getName()));
                i.putExtra("itemname", cutnull(category_list.get(position).getFoodtype()));
                i.putExtra("categoryrate", cutnull(category_list.get(position).getRate()));
                i.putExtra("image", cutnull(category_list.get(position).getImage_path()));
                i.putExtra("add", "update");
                (mContext).startActivity(i);


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


    public int getImageOrientation(String imagePath) {
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

    public String cutnull(String input) {
        if (input == null) {
            return "";
        } else if (input.equalsIgnoreCase("null")) {
            return "";
        }
        return input;
    }

}

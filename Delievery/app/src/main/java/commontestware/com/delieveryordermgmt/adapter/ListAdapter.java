package commontestware.com.delieveryordermgmt.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import commontestware.com.delieveryordermgmt.R;
import commontestware.com.delieveryordermgmt.Utills.CompressImage;
import commontestware.com.delieveryordermgmt.Utills.Popup;
import commontestware.com.delieveryordermgmt.database.DbHelper;
import commontestware.com.delieveryordermgmt.model.ModelEmployee;
import commontestware.com.delieveryordermgmt.view.CreateCategory;
import commontestware.com.delieveryordermgmt.view.Employee;
import commontestware.com.delieveryordermgmt.view.RegisterPage;

/**
 * Created by BLT0037 on 10/14/2015.
 */
public class ListAdapter extends BaseAdapter {

    private Context mContext;
    private Activity mActivity;
    ArrayList<ModelEmployee> category_list;
    LayoutInflater inflater;
    DbHelper database;

    public ListAdapter(FragmentActivity activity, ArrayList<ModelEmployee> mThumbIds) {

        this.mContext = activity;
        this.mActivity = activity;
        this.category_list = mThumbIds;
        inflater = LayoutInflater.from(activity);
        database = new DbHelper(mActivity);
    }

    public ListAdapter(Popup popup, String[] bill_foodrate, String[] bill_foodamount, String[] bill_foodquantity, int[] bill_rate, int[] bill_quantity, int[] bill_amt) {


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
        TextView name;
        TextView mobile;
        TextView id;
        ImageView emp_img, close, edit;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listitems, null);
            holder.name = (TextView) convertView.findViewById(R.id.emp_name);
            holder.mobile = (TextView) convertView.findViewById(R.id.emp_mobile);
            holder.emp_img = (ImageView) convertView.findViewById(R.id.emp_img);
            holder.close = (ImageView) convertView.findViewById(R.id.close);
            holder.edit = (ImageView) convertView.findViewById(R.id.edit);

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
        holder.emp_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

/*
                final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
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
                builder.show();*/
            }


        });

        holder.name.setText(category_list.get(position).getEmpname());
        holder.mobile.setText(category_list.get(position).getEmpmobile());


        try

        {
            if (!category_list.get(position).getImagepath().equalsIgnoreCase("")) {

                CompressImage compressImage = new CompressImage();
                Bitmap scaledBitmap = compressImage.getCompressImage(category_list.get(position).getImagepath());
                holder.emp_img.setImageBitmap(scaledBitmap);


         /*       try {

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    final Bitmap bitmap = BitmapFactory.decodeFile(category_list.get(position).getImagepath(), options);


                    Matrix matrix = new Matrix();
                    matrix.postRotate(getImageOrientation(category_list.get(position).getImagepath()));
                    Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                            bitmap.getHeight(), matrix, true);


                    Bitmap resized = Bitmap.createScaledBitmap(rotatedBitmap, 150, 150, true);

                    holder.emp_img.setImageBitmap(resized);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
*/
            }
        } catch (
                Exception e
                )

        {
            e.printStackTrace();
        }

        return convertView;
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
                boolean deleteOrNot = database.deleteContact(cutnull(category_list.get(position).getEmpid()));
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
                Intent i = new Intent(mContext, Employee.class);
                i.putExtra("empname", cutnull(category_list.get(position).getEmpname()));
                i.putExtra("empmobile", cutnull(category_list.get(position).getEmpmobile()));
                i.putExtra("empid", cutnull(category_list.get(position).getEmpid()));
                i.putExtra("image", cutnull(category_list.get(position).getImagepath()));
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
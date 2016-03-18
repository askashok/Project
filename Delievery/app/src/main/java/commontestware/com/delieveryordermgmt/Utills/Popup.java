package commontestware.com.delieveryordermgmt.Utills;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Button;
import android.widget.ScrollView;
import android.net.Uri;
import android.widget.Toast;


import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import commontestware.com.delieveryordermgmt.R;
import commontestware.com.delieveryordermgmt.adapter.CustomAdapter;
import commontestware.com.delieveryordermgmt.adapter.ListAdapter;
import commontestware.com.delieveryordermgmt.model.ModelDineIn;
import commontestware.com.delieveryordermgmt.view.BillScreen;
import commontestware.com.delieveryordermgmt.view.Helper;
import commontestware.com.delieveryordermgmt.view.Utility;


public class Popup extends Activity {


    RadioButton radioButton, radioButton1;
    ListView listView;
    CustomAdapter adapter;
    ArrayList<ModelDineIn> list = new ArrayList<>();
    ImageView email, btn_tick;
    RelativeLayout full_layout, footer1, header1, relative_id, parent_relative_layout;
    Bitmap bitmap;
    Button btn_ok;
    View header;
    TextView total_amt, tax;
    ScrollView parentscroll, childscroll;
    static ImageView img;
    Bitmap bmp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billscreen);

        //radioButton = (RadioButton) findViewById(R.id.radioButton);
        //email = (ImageView) findViewById(R.id.email);
        total_amt = (TextView) findViewById(R.id.total_amt);
        tax = (TextView) findViewById(R.id.tax);
        listView = (ListView) findViewById(R.id.bill_list_view);
        btn_tick = (ImageView) findViewById(R.id.btn_tick);
        parent_relative_layout = (RelativeLayout) findViewById(R.id.parent_relative_layout);
        btn_tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeImagePdf(parent_relative_layout, "myPdfFile.pdf");
            }
        });
        //btn_ok = (Button) findViewById(R.id.popup_ok);

     /*   btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent send = new Intent(Popup.this, BillScreen.class);
                startActivity(send);

            }
        });*/
   /*     email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeScreenshot();
            }
        });*/


       /* listView.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    listView.scrollBy(0, 1);
                }
                return false;
            }
        });*/


        list = (ArrayList<ModelDineIn>) getIntent().getSerializableExtra("yourlist");
        Log.e("yourlist", "" + list.size());
        float total = 0;
        float tax = 11;
        for (int i = 0; i < list.size(); i++) {
            total += Float.parseFloat(list.get(i).getTotalamount());
        }

        Float grosstotal = total + (total * tax / 100);
        // Float grosstotal = Float.valueOf(total * tax);
            Log.e("Gross Amount","Gross "+grosstotal);

        adapter = new CustomAdapter(this, list);
        listView.setAdapter(adapter);
        total_amt.setText((String.valueOf(grosstotal)));


        Utility.setListViewHeightBasedOnChildren(listView);


    }

   /* private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

           *//* Document document=new Document();
            String dirpath=android.os.Environment.getExternalStorageDirectory().toString();
            PdfWriter.getInstance(document, new FileOutputStream(dirpath + "/imagedemo.pdf"));
            document.open();
            Image document1 =addImage(document);
            Image im=Image.getInstance(dirpath + "/" + imageFile);  // Replace logo.png with your image name with extension
            document.add(im);
            document.close();*//*

            //openScreenshot(im);
            sendMail(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
    }*/

    public void takeImagePdf(View mRootView, String pdfName) {

        try {

            String state = Environment.getExternalStorageState();

            File pdfDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Delivery Order Management");
            if (!pdfDir.exists()) {
                pdfDir.mkdir();
            }

            Bitmap screen = loadBitmapFromView(mRootView, mRootView.getWidth(), mRootView.getHeight());

            File pdfFile = new File(pdfDir, pdfName);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            screen.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            Image image = Image.getInstance(byteArray);

            Document document = new Document(new Rectangle(image.getScaledWidth(), image.getScaledHeight()));

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));

            document.open();

            image.setAbsolutePosition(0, 0);
            image.setAlignment(Image.MIDDLE | Element.ALIGN_MIDDLE);

            document.add(image);
            document.close();

            sendPdf(pdfFile);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }


    private void sendPdf(File pdfFile) {
        Intent intent = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:")); // it's not ACTION_SEND
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Card Set ");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(pdfFile));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
        startActivity(intent);
    }


    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }

    public Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

  /*  private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);


        try {

            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);

            Bitmap b = loadBitmapFromView(v1, 720, 1080);
            Bitmap bitmap = Bitmap.createBitmap(b);
            v1.setDrawingCacheEnabled(false);

            File path = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(path);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            Document document=new Document();
            String dirpath=android.os.Environment.getExternalStorageDirectory().toString();
            PdfWriter.getInstance(document, new FileOutputStream(dirpath + "/imagedemo.pdf"));
            document.open();
            Image document1 =addImage(document);
            Image im=Image.getInstance(dirpath + "/" + mPath);  // Replace logo.png with your image name with extension
            document.add(im);
            document.close();

            sendMail(path);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }*/


    public void sendMail(File path) {

        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[]{"receiver@website.com"});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                "Delivery Order Management Bill");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                "This is an autogenerated Bill from Delivery Order Management app");
        emailIntent.setType("image/png");
        Uri myUri = Uri.parse("file://" + path);
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        finish();

       /* Document document = new Document();
        String dirpath = android.os.Environment.getExternalStorageDirectory().toString();
        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(dirpath + "/imagedemo.pdf"));
            document.open();
            Image document1 = addImage(document);
            //  Image im=Image.getInstance(dirpath + "/" + path);  // Replace logo.png with your image name with extension
            pdfWriter.add(document1);
            document.add((Element) pdfWriter);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/
    }
}
    /*public void getAllValues() {
        View parentView = null;
        int temp = 0;
        int getTotal = 0;
        int totQuan = 0;
        int quan = 0;
        for (int i = 0; i < listView.getCount(); i++) {
            parentView = getViewByPosition(i, listView);

            String getString = ((TextView) parentView
                    .findViewById(R.id.bill_rate)).getText().toString();

            getTotal = Integer.parseInt(getString);
            temp = temp + getTotal;

            totQuan = Integer.parseInt(((TextView) parentView
                    .findViewById(R.id.bill_quantity)).getText().toString());
            quan = quan + totQuan;

        }
        // se(String.valueOf(temp), String.valueOf(quan));

    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition
                + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
}*/

    /*public void getPdf() {
        relative_id = (RelativeLayout) findViewById(R.id.relative_id);
        relative_id.setDrawingCacheEnabled(true);
        Bitmap bitmap = relative_id.getDrawingCache();
        File file, f;
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            file = new File(android.os.Environment.getExternalStorageDirectory(), "TTImages_cache");
            if (!file.exists()) {
                file.mkdirs();

            }
            f = new File(file.getAbsolutePath() + File.separator + "filename" + ".png");
            FileOutputStream ostream = null;
            try {
                ostream = new FileOutputStream(f);
                bitmap.compress(Bitmap.CompressFormat.PNG, 10, ostream);
                ostream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }*/





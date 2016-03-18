package commontestware.com.delieveryordermgmt.Utills;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.widget.Button;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

import commontestware.com.delieveryordermgmt.R;
import commontestware.com.delieveryordermgmt.adapter.CustomAdapterAway;
import commontestware.com.delieveryordermgmt.model.ModelTakeAway;
import commontestware.com.delieveryordermgmt.view.Utility;

/**
 * Created by BLT0006 on 2/18/2016.
 */
public class Popupaway extends Activity {

    ListView listView1;
    CustomAdapterAway adapter;
    ArrayList<ModelTakeAway> list = new ArrayList<>();
    ImageView email1, btn_tick;
    TextView edt_delivery, delivery_name, delivery_number, total_amt, tax;
    String str, strn, string;
    Button btn_ok;
    RelativeLayout parent_relative_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billscreen1);
        //email1 = (ImageView) findViewById(R.id.email1);
        //btn_ok = (Button) findViewById(R.id.popup_ok);

      /*  btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        total_amt = (TextView) findViewById(R.id.total_amt);
        tax = (TextView) findViewById(R.id.tax);
        edt_delivery = (TextView) findViewById(R.id.delivery_text);
        delivery_name = (TextView) findViewById(R.id.delivery_name);
        delivery_number = (TextView) findViewById(R.id.delivery_number);
        parent_relative_layout = (RelativeLayout) findViewById(R.id.parent_relative_layout);

        btn_tick = (ImageView) findViewById(R.id.btn_tick);
        btn_tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeImagePdf(parent_relative_layout, "myPdfFile.pdf");
            }
        });

        Intent send = getIntent();

        str = send.getStringExtra("delivery");
        edt_delivery.setText(str);
        strn = send.getStringExtra("assignto");
        delivery_name.setText(strn);
        string = send.getStringExtra("mobile");
        delivery_number.setText(string);


      /*  email1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeScreenshot();
            }
        });*/

        listView1 = (ListView) findViewById(R.id.bill_list_view);

        listView1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        list = (ArrayList<ModelTakeAway>) getIntent().getSerializableExtra("yourlist");
        Log.e("yourlist", "" + list.size());

        float total = 0;
        float tax = 11;
        for (int i = 0; i < list.size(); i++) {
            total += Integer.parseInt(list.get(i).getTotalamount());
        }

        Float grosstotal = total + (total * tax / 100);
        // Float grosstotal = Float.valueOf(total * tax);
        Log.e("Gross Amount", "Gross " + grosstotal);

        adapter = new CustomAdapterAway(this, list);
        listView1.setAdapter(adapter);
        total_amt.setText((String.valueOf(grosstotal)));

        Utility.setListViewHeightBasedOnChildren(listView1);
    }

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
}


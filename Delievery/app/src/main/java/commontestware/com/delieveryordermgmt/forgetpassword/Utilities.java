package commontestware.com.delieveryordermgmt.forgetpassword;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;

/**
 * Created by BLT0008 on 9/24/2015.
 */
public class Utilities {
    public static void setImageDrawable(Context context, int resource, View view) {
        try {
            SVG svg = SVGParser.getSVGFromResource(context.getResources(), resource);
            PictureDrawable drawable = svg.createPictureDrawable();
            if (view instanceof ImageView) {
                ImageView imageView = (ImageView) view;
                imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                imageView.setImageDrawable(drawable);

            } else if (view instanceof TextView) {

                TextView textView = (TextView) view;
                textView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                textView.setBackground(drawable);

            } else if (view instanceof View) {

                View viewOne = (View) view;
                viewOne.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                viewOne.setBackground(drawable);

            }
        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}

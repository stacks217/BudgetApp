package edu.uci.stacks.easybudget.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.WindowManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import edu.uci.stacks.easybudget.R;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

public class ViewReceiptActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_receipt);
        ImageViewTouch imageView = (ImageViewTouch) findViewById(R.id.receipt_image);
        Bitmap receipt = null;
        try {
            InputStream stream = new FileInputStream(new File(getIntent().getStringExtra("data")));
            try {
                receipt = BitmapFactory.decodeStream(stream);
            } finally {
                stream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageView.setDisplayType(ImageViewTouchBase.DisplayType.FIT_IF_BIGGER);
        imageView.setImageBitmap(receipt, null, -1, -1);
    }
}

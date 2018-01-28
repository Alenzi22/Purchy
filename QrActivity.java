package com.example.abdullah.purchy;

/**
 * Created by Abdullah on 09/11/17.
 */
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle; import android.widget.ImageView;


public class QrActivity extends AppCompatActivity {


    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr2);
        imageView = (ImageView)
                this.findViewById(R.id.imageView);
    Bitmap bitmap = getIntent().getParcelableExtra("pic");
    imageView.setImageBitmap(bitmap);


    }

}
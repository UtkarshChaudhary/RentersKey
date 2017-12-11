package com.example.lenovo.renterskey.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.lenovo.renterskey.R;

public class UploadImages extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_images);
        Toolbar toolbar=findViewById(R.id.toolbar_uploadImages);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.cross);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}

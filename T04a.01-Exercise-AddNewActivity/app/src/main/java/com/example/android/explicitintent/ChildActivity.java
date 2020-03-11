package com.example.android.explicitintent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ChildActivity extends AppCompatActivity {

    TextView mDisplayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        mDisplayView = (TextView) findViewById(R.id.tv_display);
        Intent mainIntent = getIntent();

        if(!mainIntent.getStringExtra("textMain").equals("")){
            String mainText = mainIntent.getStringExtra("textMain");
            mDisplayView.setText(mainText);
        }


    }
}

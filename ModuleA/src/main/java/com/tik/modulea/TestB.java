package com.tik.modulea;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Modules;
import com.github.mzule.activityrouter.annotation.Router;

import static android.R.attr.id;

@Router("B/:text")
public class TestB extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modulea_testa);
        String title = getIntent().getStringExtra("text");
        TextView tv = (TextView) findViewById(R.id.testa_content);
        tv.setText(title);
    }
}

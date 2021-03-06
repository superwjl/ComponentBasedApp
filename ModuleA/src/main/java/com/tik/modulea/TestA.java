package com.tik.modulea;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.github.mzule.activityrouter.router.Routers;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;


@Router("A")
public class TestA extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modulea_testa);
        TextView tv = (TextView) findViewById(R.id.testa_content);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Routers.open(TestA.this, "common://B/I am B!");
            }
        });
    }
}

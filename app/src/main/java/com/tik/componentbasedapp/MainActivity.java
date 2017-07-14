package com.tik.componentbasedapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.mzule.activityrouter.router.Routers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void jump(View v){
        Toast.makeText(this, "jump", Toast.LENGTH_SHORT).show();
        Routers.open(MainActivity.this, "common://A");
    }
}

package com.houtrry.bubbleview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btn_bubble_relative_layout).setOnClickListener(this);
        findViewById(R.id.btn_bubble_linear_layout).setOnClickListener(this);
        findViewById(R.id.btn_bubble_image_view).setOnClickListener(this);
    }

    private void startAty(Class clazz) {
        Intent intent = new Intent(MainActivity.this, clazz);
        MainActivity.this.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bubble_relative_layout: {
                startAty(BubbleRelativeLayoutActivity.class);
                break;
            }
            case R.id.btn_bubble_linear_layout: {
                startAty(BubbleLinearLayoutActivity.class);
                break;
            }
            case R.id.btn_bubble_image_view: {
                startAty(BubbleImageViewActivity.class);
                break;
            }
            default:
                break;

        }
    }
}

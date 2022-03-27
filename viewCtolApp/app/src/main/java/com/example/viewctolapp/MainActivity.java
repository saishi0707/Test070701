package com.example.viewctolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements
        View.OnTouchListener, View.OnClickListener {
    private final String tag = MainActivity.class.getSimpleName();
    private Button resetBtn;
    private CustomImageView image;
    private int preDx, preDy;
    private Boolean defFlag = true;

    private int defDx, defDy;
    private FrameLayout viewLayout;
    private int viewLayoutDefTop;
    private int viewLayoutDefLeft;
    private int viewLayoutDefRight;
    private int viewLayoutDefBottom;

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        resetBtn = findViewById(R.id.reset_btn);
        image = findViewById(R.id.image);
        viewLayout = findViewById(R.id.view_image_layout);
        image.setOnTouchListener(this);
        resetBtn.setOnClickListener(this);
    }

    private void initSize() {
        WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
        Display disp = wm.getDefaultDisplay();
        ViewGroup.LayoutParams params = viewLayout.getLayoutParams();
        Point realSize = new Point();
        disp.getRealSize(realSize);
        Drawable drawable = getDrawable(R.drawable.ic_launcher_foreground);

        int layoutW = realSize.x;
        int layoutY = params.height;
        int screenWidth = realSize.x;
        int screenHeight = realSize.y;
        int drawableW = drawable.getMinimumWidth();
        int drawableH = drawable.getMinimumHeight();

        viewLayoutDefTop = (screenHeight / 2) - (layoutY / 2) + (drawableH / 2);
        viewLayoutDefBottom = viewLayoutDefTop + (screenHeight / 2) - (drawableH / 2);
        viewLayoutDefRight = layoutW - (drawableW / 2);
        viewLayoutDefLeft = (drawableW / 2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initSize();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // x,y 位置取得
        int newDx = (int)event.getRawX();
        int newDy = (int)event.getRawY();

        if (newDx < viewLayoutDefLeft || newDx > viewLayoutDefRight) {
            return true;
        }
        if (newDy < viewLayoutDefTop || newDy > viewLayoutDefBottom) {
            return true;
        }

        if (defFlag) {
            defDx = image.getWidth();
            defDy = image.getHeight();
            defFlag = false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                v.performClick();
                int dx = image.getLeft() + (newDx - preDx);
                int dy = image.getTop() + (newDy - preDy);
                int imgW = dx + image.getWidth();
                int imgH = dy + image.getHeight();

                image.layout(dx, dy, imgW, imgH);
                break;
            case MotionEvent.ACTION_DOWN:
                // nothing to do
                break;
            case MotionEvent.ACTION_UP:
                // nothing to do
                break;
            default:
                break;
        }
        preDx = newDx;
        preDy = newDy;
        return true;
    }

    private void resetImageView() {
        image.layout(0, 0, defDx, defDy);
        preDx = 0;
        preDy = 0;
        defFlag = true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset_btn:
                resetImageView();
                break;
            default:
                break;
        }
    }
}
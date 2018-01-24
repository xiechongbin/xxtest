package com.example.avatar;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.linchaolong.android.imagepicker.ImagePicker;

public class MainActivity extends Activity implements View.OnTouchListener {
    private ImageView iv_avatar;
    private static final int RQ_IMAGE = 1;
    private static final String[] PERMISSION_EXTERNAL_STORAGE = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int REQUEST_EXTERNAL_STORAGE = 100;
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.0f;
    private Bitmap bitmap;
    private ImagePicker imagePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        iv_avatar = findViewById(R.id.avatar);
        checkPermission();
        imagePicker = new ImagePicker();
        imagePicker.setTitle("设置头像");
        imagePicker.setCropImage(true);
        imagePicker.startGallery(this, new ImagePicker.Callback() {
            @Override
            public void onPickImage(Uri imageUri) {

            }

            @Override
            public void onCropImage(Uri imageUri) {
                super.onCropImage(imageUri);
            }
        });
        mScaleDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
            /*    mScaleFactor *= detector.getScaleFactor();
                Matrix matrix = new Matrix();
                matrix.setScale(detector.getCurrentSpanX(), detector.getCurrentSpanY());
                bitmap = Bitmap.createBitmap(bitmap, 100, 100, bitmap.getWidth(), bitmap.getHeight());
                iv_avatar.setImageBitmap(bitmap);*/
                Log.d("aass", "onscale");
                return false;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return false;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {

            }
        });
        iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RQ_IMAGE);

            }
        });
    }

    /**
     * 检查权限
     */
    private void checkPermission() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission_group.STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSION_EXTERNAL_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.onActivityResult(this, requestCode, resultCode, data);
        /*if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            String[] imagePaths = {MediaStore.Images.Media.DATA};
            Cursor cursor = this.getContentResolver().query(uri, imagePaths, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(imagePaths[0]);
            String path = cursor.getString(columnIndex);
            showImage(path);
        }*/
    }

    /**
     * 显示图片
     */
    private void showImage(String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        iv_avatar.setOnClickListener(null);
        bitmap = BitmapFactory.decodeFile(path);
        iv_avatar.setImageBitmap(bitmap);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mScaleDetector.onTouchEvent(event);
    }
}

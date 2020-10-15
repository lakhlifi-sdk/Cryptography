package com.example.apppython;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    Button btn;
    ImageView iv,iv2;
    private SweetAlertDialog pDialog, pDialog2 ;
    EditText edttext;
    private ProgressDialog dialog;
    private TextView select_picture;
    private Python py=null;
    ProgressBar progressBar;
    private TextView resulttxtview;
    private ImageButton btn_mor_options;

    Bitmap bitmap;
    private Spinner spinner;
    String imageString = "";
    String key = "";
    private int PICK_IMAGE_REQUEST = 52;
    Uri uri;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPython();

    init();

    }

    private void init() {

        select_picture=(TextView)findViewById(R.id.select_picture);
        btn = (Button)findViewById(R.id.button_crypt);
        btn_mor_options=(ImageButton)findViewById(R.id.btn_mor_options);
        iv = (ImageView)findViewById(R.id.imageView);
        iv2 = (ImageView)findViewById(R.id.imageView_res);
        edttext = (EditText)findViewById(R.id.editText_key);
        resulttxtview = (TextView) findViewById(R.id.resulttxtview);

        dialog=new ProgressDialog(MainActivity.this);
        progressBar= (ProgressBar) findViewById(R.id.progress1);

        dialog.setCancelable(false);

        pDialog2 = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        py = Python.getInstance();

        select_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = chooseImage();
            }
        });

        btn_mor_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.widget.PopupMenu popupMenu=new android.widget.PopupMenu(MainActivity.this,btn_mor_options);
                popupMenu.inflate(R.menu.menu_more_options);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.item_about_us:{
                                startActivity(new Intent(MainActivity.this,About_us.class));

                                return true;
                            }

                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });


    }


    public void crypt(View view) {
        btn.setBackgroundColor(Color.GREEN);
        btn.setText("Re-crypt");
        select_picture.setTextColor(Color.RED);
        select_picture.setText("select new picture");

        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Predicting ...");
        pDialog.setCancelButton("Cancel  ", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                pDialog.dismissWithAnimation();
            }
        });
        //pDialog.setCustomImage(R.drawable.covid_logo);
        pDialog.setCancelable(false);
        pDialog.show();
        progressBar.setVisibility(View.VISIBLE);

        dialog.setMessage("waiting ...");
        dialog.show();
        pDialog2.setTitle("your picture encrypted");
        key = edttext.getText().toString();
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG , 100 , b);
        byte[] imageBytes = b.toByteArray();
        imageString = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        PyObject PythonFile = py.getModule("mainA52");

        PyObject obj = PythonFile.callAttr("main",key,imageString);

        String str=  obj.toString();


        byte[] data = Base64.decode(str,Base64.DEFAULT);

        Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
        iv2.setImageBitmap(bitmap);
        dialog.dismiss();
        pDialog.dismissWithAnimation();
        pDialog2.show();
        progressBar.setVisibility(View.INVISIBLE);
        resulttxtview.setVisibility(View.VISIBLE);


    }


    //functions
    public Intent chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

        return  intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                iv.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public void initPython()
    {
        if(!Python.isStarted())
        {
            Python.start(new AndroidPlatform(this));
        }
    }


}

package com.example.lenovo.renterskey.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.renterskey.Networking.ApiInterface;
import com.example.lenovo.renterskey.Networking.ClientService;
import com.example.lenovo.renterskey.R;
import com.example.lenovo.renterskey.vo.UserLoginResponse;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadImages extends AppCompatActivity implements View.OnClickListener {

    private ImageButton imageButton1;
    private ImageButton imageButton2;
    private ImageButton imageButton3;
    private ImageButton imageButton4;
    private File file1;
    private File file2;
    private File file3;
    private File file4;


    public static final int PICK_IMAGE1=101;
    public static final int PICK_IMAGE2=102;
    public static final int PICK_IMAGE3=103;
    public static final int PICK_IMAGE4=104;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if(item.getItemId()== R.id.done){
               if(file1==null&&file2==null&&file3==null&&file4==null){
                   Toast.makeText(UploadImages.this,"Upload atleast one image by clicking any imagebutton shown"
                   ,Toast.LENGTH_LONG).show();

                   return true;
               }

                if(file1!=null){
                   uploadImage(file1);
                }
                if(file2!=null){
                    uploadImage(file2);
                }
                if(file3!=null){
                    uploadImage(file3);
                }
                if(file4!=null){
                    uploadImage(file4);
                }


                return true;
            }
            return false;
        }

    };
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
        BottomNavigationView navigation=findViewById(R.id.bottom_navigation_uploadImages);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        TextView textView=navigation.findViewById(R.id.done).findViewById(R.id.largeLabel);
        textView.setTextSize(28);
        textView.setPadding(0,0,0,22);

        imageButton1=findViewById(R.id.image1_imagebutton);
        imageButton1.setOnClickListener(this);
        imageButton2=findViewById(R.id.image2_imagebutton);
        imageButton2.setOnClickListener(this);
        imageButton3=findViewById(R.id.image3_imagebutton);
        imageButton3.setOnClickListener(this);
        imageButton4=findViewById(R.id.image4_imagebutton);
        imageButton4.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);




        switch (view.getId()){
            case R.id.image1_imagebutton:{
                startActivityForResult(Intent.createChooser(intent,"Select Image From Gallery"), PICK_IMAGE1);

            }
            break;
            case R.id.image2_imagebutton:{
                startActivityForResult(Intent.createChooser(intent,"Select Image From Gallery"), PICK_IMAGE2);

            }
            break;
            case R.id.image3_imagebutton:{
                startActivityForResult(Intent.createChooser(intent,"Select Image From Gallery"), PICK_IMAGE3);

            }
            break;
            case R.id.image4_imagebutton:{
                startActivityForResult(Intent.createChooser(intent,"Select Image From Gallery"), PICK_IMAGE4);

            }
            break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK && data!=null){
            if(requestCode==PICK_IMAGE1||requestCode==PICK_IMAGE2||requestCode==PICK_IMAGE3||requestCode==PICK_IMAGE4){

                android.net.Uri selectedImage=data.getData();
                String[] filePathColumn={MediaStore.Images.Media.DATA};
                android.database.Cursor cursor=getContentResolver().query(selectedImage,filePathColumn,
                        null,null,null);
                if(cursor==null){
                    Toast.makeText(this,"Try again",Toast.LENGTH_LONG).show();
                    return;
                }

                cursor.moveToFirst();
                int columnIndex=cursor.getColumnIndex(filePathColumn[0]);
                String filePath=cursor.getString(columnIndex);
                cursor.close();


                Bitmap bitmap=BitmapFactory.decodeFile(filePath);

                bitmap=getResizedBitmap(bitmap,357);

                switch (requestCode){
                    case PICK_IMAGE1:{
                       imageButton1.setBackground(null);
                       imageButton1.setImageBitmap(bitmap);
                        file1=new File(filePath);

                     }
                    break;
                    case PICK_IMAGE2:{
                        imageButton2.setBackground(null);
                        imageButton2.setImageBitmap(bitmap);
                        file2=new File(filePath);

                    }
                    break;
                    case PICK_IMAGE3:{
                      imageButton3.setBackground(null);
                      imageButton3.setImageBitmap(bitmap);
                        file3=new File(filePath);

                    }
                    break;
                    case PICK_IMAGE4:{
                       imageButton4.setBackground(null);
                       imageButton4.setImageBitmap(bitmap);
                        file4=new File(filePath);

                    }
                }

            }
        }else{
            Toast.makeText(this,"You haven't picked image",Toast.LENGTH_LONG).show();
        }
    }

    //to resize image of gallery to size of image button
    private Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private void uploadImage(File file){

        RequestBody requestBody=RequestBody.create(MediaType.parse("image/*"),file);
        MultipartBody.Part body=MultipartBody.Part.createFormData("upload",file.getName(),requestBody);
        RequestBody productId=RequestBody.create(MediaType.parse("text/plain"),"5a300f71ba9c922f286c0dd3");

        //uploadImageRequest body=new uploadImageRequest(file,"5a300f71ba9c922f286c0dd3");
        ApiInterface apiInterface = ClientService.createService();
        Call<UserLoginResponse> call= apiInterface.uploadImageOfProduct(body,productId);

        call.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                Log.d("abcdefg","inside Response respnse= "+response.toString());
                if(response.body()!=null){
                    Log.d("abcdefg","success "+response.body().isSuccess());
                }else{
                    Log.d("abcdefg","response body null");
                }
            }

            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                Log.d("abcdefg","inside failure");
            }
        });
    }

}

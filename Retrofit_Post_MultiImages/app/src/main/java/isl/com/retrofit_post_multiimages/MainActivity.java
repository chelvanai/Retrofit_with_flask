package isl.com.retrofit_post_multiimages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;
    ArrayList<Uri> mArrayUri = null;
    List<ImageViewModel> imagesViewModels = null;
    Button b1,b2,b3;
    private MainImageViewModel mainImageViewModel;
    RestClient restService = new RestClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);

        imagesViewModels = new ArrayList<>();
        mainImageViewModel = new MainImageViewModel();

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mArrayUri.clear();
                imagesViewModels.clear();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ServiceNo = "23";
                mainImageViewModel.setServiceNo(ServiceNo);

                restService.getService().GetEmpImages(mainImageViewModel, new Callback<Boolean>() {
                    @Override
                    public void success(Boolean aBoolean, Response response) {

                        if (aBoolean) {

                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {


                    }
                });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                if (data.getClipData() != null){
                    ClipData mClipData = data.getClipData();
                    if (mArrayUri == null) {
                        mArrayUri = new ArrayList<Uri>();
                    }

                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        mArrayUri.add(uri);

//                        Toast.makeText(MainActivity.this,uri.toString(),Toast.LENGTH_SHORT).show();

                        Bitmap bitmap = null;

                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            bitmap = Bitmap.createScaledBitmap(bitmap, 480, 640, false);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

                            byte[] byteArray = byteArrayOutputStream.toByteArray();
                            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                            System.out.println("Encoded :" + encoded);

                            ImageViewModel imageViewModel = new ImageViewModel();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
                            String name = dateFormat.format(new Date());
                            name = name + i;
                            imageViewModel.setImageFileName(name);
                            imageViewModel.setImageBase64File(encoded);
                            System.out.println("Image model  "+imageViewModel.getImageFileName());
                            imagesViewModels.add(imageViewModel);
                        }

                        catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


}

package org.hse.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;



public class SettingsActivity extends AppCompatActivity  {

    private SensorManager sensorManager;
    private PreferenceManager preferenceManager;
    private Sensor light;
    private EditText nameEdit;
    private TextView sensorLight;
    private ImageView userPhoto;
    Uri photoURI;

    private static final String TAG = "SettingsActivity";
    private static final String PERMISSION = "android.permission.CAMERA";
    private static final Integer REQUEST_PERMISSION_CODE = 1;
    private static final Integer REQUEST_IMAGE_CAPTURE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferenceManager = new PreferenceManager(this);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorLight = findViewById(R.id.textViewLevel);
        sensorLight.setText("0 lux");
        nameEdit = findViewById(R.id.editTextName);

        Button saveButton = findViewById(R.id.btn_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        Button uploadPhoto = findViewById(R.id.btn_make_photo);
        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });

        userPhoto = findViewById(R.id.userPhoto);

        getPhoto();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            int permissionCheck = ActivityCompat.checkSelfPermission(this, PERMISSION);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.ask_photo_perm), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            userPhoto.setImageURI(photoURI);
        }
    }

    public void checkPermission() {
        int permissionCheck = ActivityCompat.checkSelfPermission(
                this, PERMISSION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    PERMISSION)) {
                Toast.makeText(getApplicationContext(), getString(R.string.ask_photo_perm), Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{PERMISSION}, REQUEST_PERMISSION_CODE);
            }
        } else {
            dispatchTakePictureIntent();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.e(TAG, "Create file", ex);
            }
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                try {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                } catch (ActivityNotFoundException ex) {
                    Log.e(TAG, "Start activity:", ex);
                }
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        return image;
    }


    private void save() {
        if (photoURI != null) {
            preferenceManager.saveValue("photo_path", photoURI.toString());
            Toast.makeText(getApplicationContext(), getString(R.string.saved_image_msg), Toast.LENGTH_SHORT).show();
        }
    }

    private void getPhoto(){
        String uri = preferenceManager.getValue("photo_path", null);
        if (uri != null) {
            photoURI = Uri.parse(uri);
            userPhoto.setImageURI(photoURI);
        }
    }

}
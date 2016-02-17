package com.example.osorekoxuan.assignment3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity implements Callback,SensorEventListener,View.OnClickListener {

    public Camera camera;
    private Camera.Parameters parameters;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private LocationManager locationManager;
    private String gpsProvider, netProvider;
    private double latitude = 0.0, longitude = 0.0;
    private SensorManager sensorManager;
    private Vibrator vibrator;
    int flag = 0;
    ImageButton galleryButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        surfaceView = (SurfaceView) findViewById(R.id.cameraView);
        surfaceHolder = surfaceView.getHolder();  //getHolder
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        galleryButton = (ImageButton) findViewById(R.id.toGallery);
        galleryButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected  void onPause(){
        super.onPause();
        if(sensorManager != null){
            sensorManager.unregisterListener(this);
        }
        if(camera != null){
            camera.stopPreview();
            camera.release();
            camera = null;
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if(camera == null) {
            camera = Camera.open();
            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.setDisplayOrientation(getPreviewDegree(MainActivity.this));
                camera.startPreview();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        parameters = camera.getParameters();
        parameters.setPictureFormat(PixelFormat.JPEG);
        parameters.setPreviewSize(width, height);
        parameters.setPreviewFrameRate(5);
        parameters.setPictureSize(width, height);
        parameters.setJpegQuality(80);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        if(camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
            surfaceHolder = null;
            surfaceView = null;
        }
    }

    public int getPreviewDegree(Activity activity) {
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degree = 0;
        ViewGroup.MarginLayoutParams p;
        switch (rotation) {
            case Surface.ROTATION_0:
                degree = 90;
                break;
            case Surface.ROTATION_90:
                p = (ViewGroup.MarginLayoutParams) surfaceView.getLayoutParams();
                p.setMargins(0, 0, 0, 80);
                surfaceView.requestLayout();
                degree = 0;
                break;
            case Surface.ROTATION_180:
                degree = 270;
                break;
            case Surface.ROTATION_270:
                p = (ViewGroup.MarginLayoutParams) surfaceView.getLayoutParams();
                p.setMargins(0, 0, 0, 80);
                surfaceView.requestLayout();
                degree = 180;
                break;
        }
        return degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        float speed = values[0] + values[1] + values[2];
        Log.d("My app", "speed: " + speed);
        if(Math.abs(speed) > 40 && flag == 0){
            flag = 1;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getPosition();
                            takePic();
                        }
                    }, 1000);
                }
            });
        }
    }

    public void getPosition(){
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        gpsProvider = LocationManager.GPS_PROVIDER;
        if(locationManager.isProviderEnabled(gpsProvider)) {
            Location location = locationManager.getLastKnownLocation(gpsProvider);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        }
        else{
            netProvider = LocationManager.NETWORK_PROVIDER;
            locationManager.requestLocationUpdates(netProvider,1000,0,locationListener);
            Location location1 = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location1 != null) {
                latitude = location1.getLatitude();
                longitude = location1.getLongitude();
            }
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public void takePic(){
        if(camera != null) {
            vibrator.vibrate(200);
            Toast toast = new Toast(getApplicationContext());
            toast.makeText(getApplicationContext(), "Picture will be taken 3...2...", Toast.LENGTH_SHORT).show();
            camera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    if (success) {
                        parameters = camera.getParameters();
                        parameters.setPictureFormat(PixelFormat.JPEG);
                        parameters.setPreviewSize(800, 480);
                        camera.setParameters(parameters);
                        camera.takePicture(null, null, jpeg);
                    }
                }
            });
        }

    }

    Camera.PictureCallback jpeg = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // TODO Auto-generated method stub
            try {
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                String filename = format.format(date) + ".jpg";
                String locationname = "location.txt";
                String picturename = "picture.txt";
                File fileFolder = new File(Environment.getExternalStorageDirectory()
                        + "/pic/");
                if (!fileFolder.exists()) {
                    fileFolder.mkdir();
                }
                File jpgFile = new File(fileFolder, filename);
                FileOutputStream outputStream = new FileOutputStream(jpgFile);
                outputStream.write(data);
                outputStream.close();
                DecimalFormat df = new DecimalFormat("0.000");
                storeData((Environment.getExternalStorageDirectory() + "/pic/" + locationname + "/"), "Lat: " + df.format(new Double(latitude)).toString() + " Lon: " + df.format(new Double(longitude)).toString() + "\n");
                storeData((Environment.getExternalStorageDirectory() + "/pic/" + picturename + "/"), filename + "\n");
                Toast toast = new Toast(getApplicationContext());
                toast.makeText(getApplicationContext(),"Picture has been taken",Toast.LENGTH_SHORT).show();
                camera.startPreview();
                flag = 0 ;
            } catch (Exception ex) {
                // TODO Auto-generated catch block
                ex.printStackTrace();
            }
        }
    };

    public static void storeData(String fileName, String content) {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View view){
        getFragmentManager().beginTransaction().add(android.R.id.content,new GalleryFragment(),"location").commit();
    }
}

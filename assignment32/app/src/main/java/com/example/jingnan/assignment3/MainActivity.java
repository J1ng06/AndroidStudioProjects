package com.example.jingnan.assignment3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class MainActivity extends Activity implements Camera.PictureCallback,
        SurfaceHolder.Callback,
        AccelerometerListener, SensorEventListener {

    private static final double ASPECT_TOLERANCE = 0.1;
    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
//    private ArrayList<Bitmap> mImageList;
    private LocationManager locationManager;
    private String gpsProvider, netProvider;
    private double latitude = 0.0, longitude = 0.0;
    public static String picLocation = "";
    Camera.PictureCallback jpegCallback;
    Camera.Parameters params;
    final String LOG_TAG ="Camera";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSurfaceView = (SurfaceView) findViewById(R.id.cameraView);
        mSurfaceHolder = mSurfaceView.getHolder();

        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(mSurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        jpegCallback = new Camera.PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                onPictureJpeg(data,camera,MainActivity.this);
        };

    };
    }
    void onPictureJpeg(byte[] bytes, Camera camera,Context context){
        String usermessage = null;
        String addressMessage = null;

        int i = bytes.length;
        Log.d(LOG_TAG, String.format("bytes= %d", i));

        picLocation = "lati_ " + latitude + " long_ " + longitude;
        File f = Helper.generatePhotoFile(picLocation);
        String path = f.getPath();
        setDefaults(path, picLocation, MainActivity.this);//Store the File path and Address information as Key Value Pair on the SharedPreference Memory
        try{
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(f));
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
            usermessage = "Picture saved as " + f.getName()+"\n"+ getDefaults(path,this);
            Log.e(LOG_TAG,"image saved in : " + f.getAbsolutePath());

        }catch(Exception e){
            Log.e(LOG_TAG,"Error accessing photo output file: " + e.getMessage());
            usermessage = "Error saving photo";

        }
        camera.startPreview();


        //sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
        //Uri.parse("file://" + Environment.getExternalStorageDirectory())));

    }
    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
//    public void captureImage(View v) throws IOException {
//        mCamera.takePicture(null, null, jpegCallback);
//    }

    public void refreshCamera() {
        if (mSurfaceHolder.getSurface() == null) {
            return;
        }
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
        }

        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();
        } catch (Exception e) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Check device supported Accelerometer senssor or not
        if (AccelerometerManager.isListening()) {

            //Start Accelerometer Listening
            AccelerometerManager.stopListening();

            Toast.makeText(getBaseContext(), "Accelerometer Stoped",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void galleryViewBtnClicked(View v) {
        Intent intent = new Intent(this, GridViewActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera = Camera.open();

            params = mCamera.getParameters();
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            try {
                mCamera.setPreviewDisplay(mSurfaceHolder);
                mCamera.setDisplayOrientation(getPreviewDegree(MainActivity.this));
                mCamera.startPreview();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            mCamera.setParameters(params);

        } catch (RuntimeException e) {
            System.err.println(e);
            return;
        }

        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();
        } catch (Exception e) {
            System.err.println(e);
            return;
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
                p = (ViewGroup.MarginLayoutParams) mSurfaceView.getLayoutParams();
                p.setMargins(0, 0, 0, 80);
                mSurfaceView.requestLayout();
                degree = 0;
                break;
            case Surface.ROTATION_180:
                degree = 270;
                break;
            case Surface.ROTATION_270:
                p = (ViewGroup.MarginLayoutParams) mSurfaceView.getLayoutParams();
                p.setMargins(0, 0, 0, 80);
                mSurfaceView.requestLayout();
                degree = 180;
                break;
        }
        return degree;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("MYDEBUG", "surfaceChanged");
        refreshCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("MYDEBUG", "surfaceDestroyed");
        this.closeCamera(mCamera);
    }

    public void imageCapture() throws IOException {
        mCamera.takePicture(null, null, jpegCallback);
    }

/*    public void galleryViewBtnClicked(View v) {
        Intent intent = new Intent(this, ImageViews.class);
        startActivity(intent);
    }*/

    private void closeCamera(Camera camera) {
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        refreshCamera();
    }



    private Location getGpsLocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {

            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return location;
        }
        return null;
    }

    public void onAccelerationChanged(float x, float y, float z) {
        // TODO Auto-generated method stub

    }

    public void onShake(float force) {
        // Called when Motion Detected
        Toast.makeText(getBaseContext(), "Taking Picture",
                Toast.LENGTH_SHORT).show();
        getPosition();
        if (AccelerometerManager.isListening()) {
            //Start Accelerometer Listening
            AccelerometerManager.stopListening();
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                mCamera.takePicture(null, null, jpegCallback);

                listenOnAccelerometer();
            }
        }, 1000);

    }

    @Override
    public void onResume() {
        super.onResume();


        Toast.makeText(getBaseContext(), "Accelerometer Started",
                Toast.LENGTH_SHORT).show();

        listenOnAccelerometer();
    }

    @Override
    public void onStop() {
        super.onStop();

        //Check device supported Accelerometer senssor or not
        if (AccelerometerManager.isListening()) {

            //Start Accelerometer Listening
            AccelerometerManager.stopListening();

            /*Toast.makeText(getBaseContext(), "onStop Accelerometer Stoped",
                    Toast.LENGTH_SHORT).show();*/
        }

    }

    public void listenOnAccelerometer() {
        //Check device supported Accelerometer senssor or not
        if (AccelerometerManager.isSupported(this)) {

            //Start Accelerometer Listening
            AccelerometerManager.startListening(this);
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
        Log.d("DEBUG", latitude+ "  " + longitude + "\n");
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

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    @Override
    public void onSensorChanged(SensorEvent event) {}
    public boolean isAspectRatioMatch(double ratio1, double ratio2) {
        if (Math.abs(ratio1 - ratio2) > ASPECT_TOLERANCE) {
            return false;
        }
        return true;
    }
}

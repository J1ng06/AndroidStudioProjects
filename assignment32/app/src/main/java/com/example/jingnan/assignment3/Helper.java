package com.example.jingnan.assignment3;

import android.content.Context;
import android.hardware.Camera;
import android.os.Environment;
import android.view.Surface;
import android.view.WindowManager;

import java.io.File;

/**
 * Created by jingnan on 16/2/8.
 */
public class Helper {

   public static int getDisplayOrientationForCamera(Context context, int cameraId){

        final int DEGREES_IN_CIRCLE = 360;
        int temp = 0;
        int previewOrientation = 0;

        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId,cameraInfo);

        int deviceOrientation = getDeviceOrientationDegree(context);
        return previewOrientation;

    }

    private static int getDeviceOrientationDegree(Context context) {

        int degrees = 0;
        WindowManager windowManager =
                (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        int rotation = windowManager.getDefaultDisplay().getRotation();
        switch(rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        return degrees;

    }

    public static File generatePhotoFile(String location){
        File photoFile = null;
        File outputDir = getPhotoDir();

        if(outputDir != null){
            String photoFileName = location  + ".jpg";
            photoFile = new File(outputDir, photoFileName);
        }
        return photoFile;

    }
    public static File getPhotoDir() {
        File picDirectory = null;
        File pictureDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        picDirectory = new File(pictureDir,"Jing");
        if(!picDirectory.exists()){
            if(!picDirectory.mkdirs()){
                picDirectory = null;
            }
        }
        return picDirectory;
    }


}

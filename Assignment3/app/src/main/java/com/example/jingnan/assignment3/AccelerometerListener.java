package com.example.jingnan.assignment3;

/**
 * Created by JINGNAN on 2016-02-08.
 */
public interface AccelerometerListener {

    public void onAccelerationChanged(float x, float y, float z);

    public void onShake(float force);

}

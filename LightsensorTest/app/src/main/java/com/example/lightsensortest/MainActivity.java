package com.example.lightsensortest;

import static com.example.lightsensortest.R.*;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    public static final String TAG="Softa";
    private SensorManager mSensorManager;
    private Sensor mSensorLight;

    private    String value="";
    private TextView mTextSensorLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        Log.d(TAG,"Started");

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorLight=mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mTextSensorLight = (TextView) findViewById(id.label_light);

        String sensor_error = getResources().getString(R.string.error_no_sensor);

        if(mSensorLight==null){
            mTextSensorLight.setText(sensor_error);
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        float currentValue = event.values[0];
        switch (sensorType) {
            case Sensor.TYPE_LIGHT:
                Log.d(TAG, "event happened:" + currentValue);
                mTextSensorLight.setText((getResources().getString(string.label_light, currentValue)));
                break;
            default:
                //nothing
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mSensorLight!=null){
            mSensorManager.registerListener(this, mSensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }
}
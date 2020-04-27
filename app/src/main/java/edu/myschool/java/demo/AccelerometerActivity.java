package edu.myschool.java.demo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mySensorManager;
    private Sensor myAccelerometer;
    private TextView textTitle;
    private TextView textContent;
    private float x=0;
    private float y=0;
    private float z=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        myAccelerometer = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        textTitle = (TextView) findViewById(R.id.textTitle);
        textTitle.setText("Accelerometer");
        textContent = (TextView) findViewById(R.id.textContent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //as you wish
    }

    @Override
    protected void onResume() {
        super.onResume();
        mySensorManager.registerListener((SensorEventListener) this, myAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mySensorManager.unregisterListener((SensorEventListener) this);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        float threshold  = 20.0f;
        textContent.setText("");
        for(float item: event.values){
            textContent.append(item + "\n");
        }
        float force = Math.abs((x - event.values[0]) + (y - event.values[1]) + (z - event.values[2]));
        if(force > threshold) {
            Toast.makeText(getApplicationContext(),"SHAKE IT", Toast.LENGTH_SHORT).show();
        }
        x = event.values[0];
        y = event.values[1];
        z = event.values[2];
    }
}

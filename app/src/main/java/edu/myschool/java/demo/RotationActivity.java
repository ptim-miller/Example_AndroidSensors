package edu.myschool.java.demo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RotationActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager mySensorManager;
    private Sensor myVector;
    private TextView textTitle;
    private TextView textContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation);
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        myVector = mySensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        textTitle = (TextView) findViewById(R.id.textTitle);
        textTitle.setText("Rotation Vector");
        textContent = (TextView) findViewById(R.id.textContent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //as you wish
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        textContent.setText("");
        for(float item: event.values){
            textContent.append(item + "\n");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mySensorManager.registerListener((SensorEventListener) this, myVector, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mySensorManager.unregisterListener(this, myVector);
    }
}

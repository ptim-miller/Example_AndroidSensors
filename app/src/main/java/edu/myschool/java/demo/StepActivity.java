package edu.myschool.java.demo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StepActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mySensorManager;
    private Sensor myStepDetect;
    private Sensor myStepCounter;
    private TextView textTitle;
    private TextView textContent;
    private ImageView imageDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        myStepDetect = mySensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        myStepCounter = mySensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        textTitle = (TextView) findViewById(R.id.textTitle);
        textTitle.setText("Steps");
        textContent = (TextView) findViewById(R.id.textContent);
        textContent.setText("0");
        imageDot = (ImageView) findViewById(R.id.imgStep);
        imageDot.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        textContent.setText("");
        if (event.sensor == myStepDetect) {
            imageDot.setVisibility(View.VISIBLE);
        } else if (event.sensor == myStepCounter){
            textContent.append("Counter: " + event.values[0] + "\n");
            imageDot.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mySensorManager.registerListener((SensorEventListener) this, myStepDetect, SensorManager.SENSOR_DELAY_NORMAL);
        mySensorManager.registerListener((SensorEventListener) this, myStepCounter, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mySensorManager.unregisterListener(this, myStepDetect);
        mySensorManager.unregisterListener(this, myStepCounter);
    }
}

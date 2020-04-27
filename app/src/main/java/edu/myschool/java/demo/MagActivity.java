package edu.myschool.java.demo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MagActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mySensorManager;
    private Sensor myMagnetometer;
    private Sensor myAccelerometer;
    private float[] myLastMagnetometer = new float[3];
    private float[] myLastAccelerometer = new float[3];
    private TextView textTitle;
    private TextView textContent;
    private float[] myR = new float[9];
    private float[] myOrientation = new float[3];
    private float myDegrees = 0;
    ImageView imgArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mag);
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        myMagnetometer = mySensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        myAccelerometer = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        textTitle = (TextView) findViewById(R.id.textTitle);
        textTitle.setText("Magnetometer");
        textContent = (TextView) findViewById(R.id.textContent);
        imgArrow = (ImageView) findViewById(R.id.imgArrow);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //as you wish
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == myMagnetometer) {
            System.arraycopy(event.values, 0, myLastMagnetometer, 0, event.values.length);
            textContent.setText("");
            for(float item: event.values){
                textContent.append(item + "\n");
            }
        } else if (event.sensor == myAccelerometer) {
            System.arraycopy(event.values, 0, myLastAccelerometer, 0, event.values.length);
        }
        SensorManager.getRotationMatrix(myR, null, myLastAccelerometer, myLastMagnetometer);
        SensorManager.getOrientation(myR, myOrientation);
        float azimuthInRadians = myOrientation[0];
        float azimuthInDegress = (float)(Math.toDegrees(azimuthInRadians) + 360) % 360;

        RotateAnimation animationRot = new RotateAnimation(
                myDegrees,azimuthInDegress * -1, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animationRot.setDuration(200);
        animationRot.setFillAfter(true);

        imgArrow.startAnimation(animationRot);
        myDegrees = azimuthInDegress * -1;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mySensorManager.registerListener((SensorEventListener) this, myMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        mySensorManager.registerListener(this, myAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mySensorManager.unregisterListener(this, myMagnetometer);
        mySensorManager.unregisterListener(this, myAccelerometer);
    }
}

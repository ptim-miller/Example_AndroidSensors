package edu.myschool.java.demo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    TextView textTitle=null;
    TextView textContent=null;
    private SensorManager mySensorManager;
    private Sensor sensor;
    boolean sensorExists = false;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int activity = ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION);
        int body = ContextCompat.checkSelfPermission(this, Manifest.permission.BODY_SENSORS);

        List<String> listPermissions = new ArrayList<>();

        if (activity != PackageManager.PERMISSION_GRANTED) {
            listPermissions.add(Manifest.permission.ACTIVITY_RECOGNITION);
        }
        if (body != PackageManager.PERMISSION_GRANTED) {
            listPermissions.add(Manifest.permission.BODY_SENSORS);
        }
        if (!listPermissions.isEmpty())
        {
            ActivityCompat.requestPermissions(this,listPermissions.toArray
                    (new String[listPermissions.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
        }

        Button btnAcc = (Button)findViewById(R.id.btnAcc);
        btnAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AccelerometerActivity.class));
            }
        });

        Button btnLight = (Button)findViewById(R.id.btnLight);
        btnLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LightActivity.class));
            }
        });

        Button btnMag = (Button)findViewById(R.id.btnMag);
        btnMag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MagActivity.class));
            }
        });

        Button btnRV = (Button)findViewById(R.id.btnRV);
        btnRV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RotationActivity.class));
            }
        });

        Button btnSteps = (Button)findViewById(R.id.btnSteps);
        btnSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StepActivity.class));
            }
        });


        textTitle = (TextView) findViewById(R.id.textTitle);
        textContent = (TextView) findViewById(R.id.textContent);
        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> myList= mySensorManager.getSensorList(Sensor.TYPE_ALL);

        for (int i = 0; i < myList.size(); i++) {
            textContent.append("Name:" + myList.get(i).getName() + "\n Max Range:" + myList.get(i).getMaximumRange() + "\n\n");
        }

        mySensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = mySensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if(sensor != null){
            sensorExists = true;
            mySensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        textTitle.setText("Steps: " + event.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do stuff here
    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        mySensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        mySensorManager.unregisterListener(this);
    }
}

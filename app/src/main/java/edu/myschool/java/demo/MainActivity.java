package edu.myschool.java.demo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.List;

// Quick intro to sensors - needs a lot of work and testing
// Split into different activities to separate functionality for students
public class MainActivity extends AppCompatActivity {
    TextView textTitle=null;
    TextView textContent=null;

    @SuppressLint("InlinedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SensorManager mySensorManager;
        Sensor sensor;
        int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
        List<String> listPermissions = new ArrayList<>();
        int activity = 0;
        boolean isV10orUp = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q);
        if (isV10orUp) {
            activity = ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION);
        }

        if (isV10orUp && activity != PackageManager.PERMISSION_GRANTED) {
            listPermissions.add(Manifest.permission.ACTIVITY_RECOGNITION);
        }

        // Not needed until we use ut - just an example
        int body = ContextCompat.checkSelfPermission(this, Manifest.permission.BODY_SENSORS);
        if (body != PackageManager.PERMISSION_GRANTED) {
            listPermissions.add(Manifest.permission.BODY_SENSORS);
        }
        if (!listPermissions.isEmpty())
        {
            ActivityCompat.requestPermissions(this,listPermissions.toArray
                    (new String[0]),REQUEST_ID_MULTIPLE_PERMISSIONS);
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
        List<Sensor> myList= null;
        if (mySensorManager != null) {
            myList = mySensorManager.getSensorList(Sensor.TYPE_ALL);
            for (int i = 0; i < myList.size(); i++) {
                textContent.append("Name:" + myList.get(i).getName() + "\n Max Range:" + myList.get(i).getMaximumRange() + "\n\n");
            }
        }
    }
}

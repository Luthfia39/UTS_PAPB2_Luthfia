package com.example.uts_papb2_luthfia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mSensorLight;
    private TextView mValueSensor;
    private float currentValue;
    private boolean isDescDisplayed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        inisialisasi
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        untuk mengatur kecerahan cahaya pada layar/mengubah mode kecerahan layar ke otomatis
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        mValueSensor = findViewById(R.id.valueSensor);

        displayFragment(LightSensorFragment.newInstance());

        TextView seeMore_btn = findViewById(R.id.btn_seeMore);
        seeMore_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment;
                if (isDescDisplayed){
                    fragment = LightSensorFragment.newInstance();
                    seeMore_btn.setText(R.string.label_seeMore);
                    displayFragment(fragment);
                    isDescDisplayed = false;

                }
                else {
                    fragment = AllSensorsFragment.newInstance();
                    seeMore_btn.setText(R.string.label_seeLightSensor);
                    displayFragment(fragment);
                    isDescDisplayed = true;
                }
            }
        });

        findViewById(R.id.btn_to_maps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent valueIntent = new Intent(MainActivity.this, MapsActivity.class);
                valueIntent.putExtra("VALUE", currentValue);
                startActivity(valueIntent);
            }
        });
    }

    private void displayFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frame_layout, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_language:
                Intent languageIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(languageIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mSensorLight != null){
            mSensorManager.registerListener(this, mSensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
//        cari tahu jenis sensor yang berubah
        int sensorType = sensorEvent.sensor.getType();
        currentValue = sensorEvent.values[0];
        switch (sensorType){
            case Sensor.TYPE_LIGHT:
                mValueSensor.setText(String.format(" %1$.2f", currentValue));
                break;
            default:
        };
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
package com.example.uts_papb2_luthfia;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LightSensorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LightSensorFragment extends Fragment implements SensorEventListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SensorManager mSensorManager;
    private Sensor mSensorLight;
    private StringBuilder sensorText;

    public LightSensorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LightSensorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LightSensorFragment newInstance(String param1, String param2) {
        LightSensorFragment fragment = new LightSensorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static LightSensorFragment newInstance() {
        LightSensorFragment fragment = new LightSensorFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }

//        inisialisasi
        mSensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
//        menampung sensor, tipe data : object sensor
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_LIGHT);
//        cek nama sensor, diubah menjadi string, kemudian disimpan
        sensorText = new StringBuilder();

        for (Sensor currentSensor : sensorList){
            sensorText.append(currentSensor.getName()).append(System.getProperty("line.separator"));
        }

//        untuk mengatur kecerahan cahaya pada layar/mengubah mode kecerahan layar ke otomatis
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_light_sensor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        menampilkan daftar sensor
        TextView sensorTextView = view.findViewById(R.id.sensorLightList);
        sensorTextView.setText(sensorText);

//        cek sensor apakah ada atau tidak
        String sensor_error = "No Sensor";
        if (mSensorLight == null){
            sensorTextView.setText(sensor_error);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
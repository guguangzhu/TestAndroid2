package com.example.testandroid2;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class LightSensorActivity extends AppCompatActivity implements SensorEventListener {

    /** Called when the activity is first created. */
    SensorManager sensorManager = null;//传感器管理器引用
    Sensor lightSensor = null;//光线传感器引用

    //各个文本的引用
    TextView accuracy_view= null;
    TextView value_0 = null;
    TextView value_1 = null;
    TextView value_2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_sensor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //获得传感器管理器实例
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
//获得光线传感器实例
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
//获得各个TextView
        accuracy_view = (TextView)findViewById(R.id.accuracy);
        value_0 = (TextView)findViewById(R.id.value_0);
        value_1 = (TextView)findViewById(R.id.value_1);
        value_2 = (TextView)findViewById(R.id.value_2);
    }





    @Override
    protected void onPause() {
// TODO Auto-generated method stub
        super.onPause();
//注销
        sensorManager.unregisterListener(this, lightSensor);
    }

    @Override
    protected void onResume() {
// TODO Auto-generated method stub
        super.onResume();
//为传感器管理器注册监听
        sensorManager.registerListener(this,lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
// TODO Auto-generated method stub
        if(sensor.getType() == Sensor.TYPE_LIGHT){
//设置将accuracy的值显示到屏幕上
            accuracy_view.setText("accuracy:"+accuracy);
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
// TODO Auto-generated method stub
        if(event.sensor.getType() == Sensor.TYPE_LIGHT){

//将values的值显示到屏幕上
            float[] values = event.values;
            value_0.setText("value[0]:"+values[0]);
            value_1.setText("value[1]:"+values[1]);
            value_2.setText("value[2]:"+values[2]);
        }
    }


}

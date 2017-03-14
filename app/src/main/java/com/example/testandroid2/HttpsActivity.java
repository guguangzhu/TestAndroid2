package com.example.testandroid2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.testandroid2.bean.TicketsBean;
import com.example.testandroid2.jni.LoadLib;
import com.example.testandroid2.net.common.RequestCallback;
import com.example.testandroid2.net.common.RequestQueue;
import com.example.testandroid2.net.enums.Task;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class HttpsActivity extends AppCompatActivity implements RequestCallback {

    @InjectView(R.id.textView)
    TextView textView;
    @InjectView(R.id.button2)
    Button button2;
    @InjectView(R.id.button3)
    Button button3;
    @InjectView(R.id.button4)
    Button button4;
    @InjectView(R.id.textView2)
    TextView textView2;

    private TicketsBean ticketsBean;

    LoadLib load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_https);
        ButterKnife.inject(this);
        load = new LoadLib();
    }


    private void getTicketResource() {
        Map<String, String> params = new HashMap<>();
        params.put("purpose_codes", "ADULT");
        params.put("leftTicketDTO.to_station", "BJP");
        params.put("leftTicketDTO.from_station", "CUW");
        params.put("leftTicketDTO.train_date", "2016-05-25");


        RequestQueue.addToQueue(this, this, true, Task.TEST_HTTPS, params);
    }

    @Override
    public void response(Task task, Object result) {
        switch (task) {
            case TEST_HTTPS:
                ticketsBean = (TicketsBean) result;
                break;
        }
    }


    @OnClick({R.id.button2, R.id.button3,R.id.button4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button2:
                getTicketResource();
                break;
            case R.id.button3:
                textView.setText("JNI测试111+222返回结果：" + load.addInt(111, 222));
                break;
            case R.id.button4:
                textView2.setText("stringFromJNI:" + load.stringFromJNI());
                break;
        }
    }
}

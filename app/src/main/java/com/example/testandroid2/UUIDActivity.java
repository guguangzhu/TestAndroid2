package com.example.testandroid2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class UUIDActivity extends AppCompatActivity {

    @InjectView(R.id.textView3)
    TextView textView3;
    @InjectView(R.id.button5)
    Button button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uuid);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.button5)
    public void onClick() {
        String hehe="hehehehe";
        UUID uuid = null;
        try {
            uuid = UUID.nameUUIDFromBytes(hehe.getBytes("utf8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(uuid!=null)
            textView3.setText(uuid.toString());
    }
}

package com.example.testandroid2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testandroid2.bean.ResultBean;
import com.example.testandroid2.net.common.RequestCallback;
import com.example.testandroid2.net.common.RequestQueue;
import com.example.testandroid2.net.enums.Task;
import com.facebook.network.connectionclass.ConnectionClassManager;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class TextInputActivity extends ToolBarActivity implements RequestCallback, View.OnClickListener, Observer<Integer> {
    private Button btnClick;
    Observable<ResultBean> observable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_input);
        initEditText();
        btnClick = (Button) findViewById(R.id.button);
        btnClick.setOnClickListener(this);
        initRxJava();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.text_input, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initRxJava() {
//        observable.subscribe(this);
    }


    private void initEditText() {
        final TextInputLayout textInputLayout = (TextInputLayout) findViewById(R.id.til_pwd);

        EditText editText = textInputLayout.getEditText();
        textInputLayout.setHint("Username");
        if (editText == null)
            return;
        editText.setTextColor(Color.BLACK);
        editText.setText(ConnectionClassManager.getInstance().getCurrentBandwidthQuality().toString());

        if (editText != null) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                    if (s.length() > 4) {
                        textInputLayout.setError("Username Error");
                        textInputLayout.setErrorEnabled(true);
                    } else {
                        textInputLayout.setErrorEnabled(false);
                    }
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before,
                                          int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
        final TextInputLayout textInputLayout2 = (TextInputLayout) findViewById(R.id.til_pwd2);

        EditText editText2 = textInputLayout.getEditText();
        textInputLayout2.setHint("Password");

        if (editText2 != null) {
            editText2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                    if (s.length() > 4) {
                        textInputLayout2.setError("Password error");
                        textInputLayout2.setErrorEnabled(true);
                    } else {
                        textInputLayout2.setErrorEnabled(false);
                    }
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before,
                                          int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }

    @Override
    public void response(Task task, Object result) {
        Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {
        RequestQueue.addToQueue(this, this, true, Task.LATEST_NEWS, null);
    }


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Integer value) {

        Timber.e("onNext %d", value);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {
    }
//
//    Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
//
//        @Override
//        public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//            e.onNext(1);
//            e.onNext(2);
//            e.onComplete();
//        }
//    });
}

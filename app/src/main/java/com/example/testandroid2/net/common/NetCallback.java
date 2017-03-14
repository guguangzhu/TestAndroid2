package com.example.testandroid2.net.common;

import com.example.testandroid2.net.enums.Task;

import java.io.IOException;

/**
 * Created by Guge on 16/1/26.
 */
public interface NetCallback {


    public void onSuccess(String result, Task task);

    public void onError(IOException e, Task task);
}

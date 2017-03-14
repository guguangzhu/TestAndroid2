package com.example.testandroid2.net.common;

import com.example.testandroid2.net.enums.Task;

/**
 * Created by Guge on 16/1/26.
 */
public interface RequestCallback<T> {
    //任务完成后的UI界面更新操作
    public void response(Task task, T result);
}

package com.example.testandroid2.bean;

import com.example.testandroid2.net.enums.Task;

/**
 * 类描述：
 * 创建人：G.G.Z
 * 创建时间：2017/2/16 15:45
 */
public class ResultBean {
    private String code="";
    private String msg="";
    private Task task;
    private Object data;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

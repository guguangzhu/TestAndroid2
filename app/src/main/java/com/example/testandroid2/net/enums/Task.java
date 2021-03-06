package com.example.testandroid2.net.enums;

import com.example.testandroid2.bean.NewsBean;
import com.example.testandroid2.bean.TicketsBean;
import com.example.testandroid2.net.config.HttpConfig;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public enum Task {
	/**
	 * 登陆
	 */
//	LOGIN(0x000000001, "登录", IConfig.URL, HttpMethod.GET);
	LATEST_NEWS(0x000000001, "最新新闻", HttpConfig.URL + "news/latest", HttpConfig.GET,new TypeToken<NewsBean>() {
	}.getType()),
	TEST_HTTPS(0x000000002, "https测试", "https://kyfw.12306.cn/otn/leftTicket/query", HttpConfig.GET,new TypeToken<TicketsBean>() {
	}.getType()),
	TEST_HTTP_2(0x000000003, "https测试", "http://101.201.116.119:5645/popularityRank/getList?bookType=&rankType=1&pageNum=1&pageSize=20&classificationType=&classificationId=&press=&yearStart=&desc=0&yearEnd=&keyword=888878&upYearStartVal=&upYearEndVal=", HttpConfig.GET,new TypeToken<TicketsBean>() {
	}.getType()),
	TEST_REQUEST(0x000000004, "http测试", "http://101.201.116.210:7726/popularityRank/getList?pageNum=1&pageSize=15", HttpConfig.GET,new TypeToken<TicketsBean>() {
	}.getType());

	private int taskID;
	private String taskName;
	private String url;
	private int type;
	private Type jsonType;

	private Task(int taskID,String taskName, String url, int type,Type jsonType) {
		this.taskName = taskName;
		this.url = url;
		this.type = type;
		this.jsonType=jsonType;
	}
	public int getTaskID(){
		return taskID;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getUrl() {
		return url;
	}

	public int getType() {
		return type;
	}

	public Type getJsonType() {
		return jsonType;
	}
}

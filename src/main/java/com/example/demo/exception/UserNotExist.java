package com.example.demo.exception;

/**
 * @Author jiaqi.qu@hand-china.com 2020/6/8 17:25
 * @Version 1.0
 */
public class UserNotExist extends RuntimeException {
	//定义无参构造方法
	public UserNotExist() {
		super();
	}

	//定义有参构造方法
	public UserNotExist(String message) {
		super(message);
	}

}

package com.example.demo.controller;

import com.example.demo.DemoApplication;
import com.example.demo.service.UserService;
import com.sun.media.jfxmedia.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class LoginTest {
	@Autowired
    private UserService userService;
	//正确输入
    @Test
    public void LoginTest1() {
    	String msg =userService.LoginFilter("100001", "se654321");
    	System.out.println(msg);
    }
    //id为空
    @Test
    public void LoginTest2() {
    	String msg =userService.LoginFilter("", "se654321");
    	System.out.println(msg);
    }
    //密码为空
    @Test
    public void LoginTest3() {
    	String msg =userService.LoginFilter("100001", "");
    	System.out.println(msg);
    }
    //密码长度超过8
    @Test
    public void LoginTest4() {
    	String msg =userService.LoginFilter("100001", "se6543210");
    	System.out.println(msg);
    }
    //id为7位数字
    @Test
    public void LoginTest5() {
    	String msg =userService.LoginFilter("1000010", "se654321");
    	System.out.println(msg);
    }
    //id为5位数字
    @Test
    public void LoginTest6() {
    	String msg =userService.LoginFilter("10001", "se654321");
    	System.out.println(msg);
    }
    //id包含字母
    @Test
    public void LoginTest7() {
    	String msg =userService.LoginFilter("a10001", "se654321");
    	System.out.println(msg);
    }
    //密码由纯数字组成
    @Test
    public void LoginTest8() {
    	String msg =userService.LoginFilter("100001", "11111111");
    	System.out.println(msg);
    }
    //密码由纯字母组成
    @Test
    public void LoginTest9() {
    	String msg =userService.LoginFilter("100001", "aaaaaaaa");
    	System.out.println(msg);
    }
    //密码包含特殊字符
    @Test
    public void LoginTest10() {
    	String msg =userService.LoginFilter("100001", "se?54321");
    	System.out.println(msg);
    }
    //id包含前置空格
    @Test
    public void LoginTest11() {
    	String msg =userService.LoginFilter(" 100001", "se654321");
    	System.out.println(msg);
    }
    //id包含后置空格
    @Test
    public void LoginTest12() {
    	String msg =userService.LoginFilter("100001 ", "se654321");
    	System.out.println(msg);
    }
    //密码包含前置空格
    @Test
    public void LoginTest13() {
    	String msg =userService.LoginFilter("100001", " se654321");
    	System.out.println(msg);
    }
    //密码包含后置空格
    @Test
    public void LoginTest14() {
    	String msg =userService.LoginFilter("100001", "se654321 ");
    	System.out.println(msg);
    }
    //id中包含空格
    @Test
    public void LoginTest15() {
    	String msg =userService.LoginFilter("100 01", "se654321");
    	System.out.println(msg);
    }
    //密码中包含空格
    @Test
    public void LoginTest16() {
    	String msg =userService.LoginFilter("100001", "se 54321");
    	System.out.println(msg);
    }
}
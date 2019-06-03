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
public class RegisterTest {
	@Autowired
    private UserService userService;
	//正确输入
    @Test
    public void RegisterTest1() {
    	String msg =userService.RegisterFilter("se654321");
    	System.out.println(msg);
    }
    //密码为空
    @Test
    public void LoginTest2() {
    	String msg =userService.RegisterFilter("");
    	System.out.println(msg);
    }
    //密码长度超过8
    @Test
    public void RegisterTest3() {
    	String msg =userService.RegisterFilter("se6543210");
    	System.out.println(msg);
    }
    //密码由纯数字组成
    @Test
    public void RegisterTest4() {
    	String msg =userService.RegisterFilter("11111111");
    	System.out.println(msg);
    }
    //密码由纯字母组成
    @Test
    public void RegisterTest5() {
    	String msg =userService.RegisterFilter("aaaaaaaa");
    	System.out.println(msg);
    }
    //密码包含特殊字符
    @Test
    public void RegisterTest6() {
    	String msg =userService.RegisterFilter("se?54321");
    	System.out.println(msg);
    }
    //密码包含前置空格
    @Test
    public void RegisterTest7() {
    	String msg =userService.RegisterFilter(" se654321");
    	System.out.println(msg);
    }
    //密码包含后置空格
    @Test
    public void RegisterTest8() {
    	String msg =userService.RegisterFilter("se654321 ");
    	System.out.println(msg);
    }
    //密码中包含空格
    @Test
    public void RegisterTest9() {
    	String msg =userService.RegisterFilter("se 54321");
    	System.out.println(msg);
    }
}
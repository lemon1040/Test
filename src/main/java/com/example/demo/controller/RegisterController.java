package com.example.demo.controller;


import com.example.demo.entity.User;
import com.example.demo.entity.result.ResultEntity;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;
    /**
     * 注册主页面
     */
    @RequestMapping(value ="/Register",method = RequestMethod.GET)
    public String Register(){
        return "Register";
    }

    /**
     * 注册业务处理页面
     */
    @RequestMapping(value ="/Register",method = RequestMethod.POST)
    public String toRegister(@RequestParam("name") String name,@RequestParam("pwd")String pwd, @RequestParam("pwd_again")String pwd_again,
                        Map<String, Object> map,HttpServletRequest request){
        if(name==null||pwd==null){
            map.put("errMsg","Illegal input");
            return "Register";
        }
        if(!pwd.equals(pwd_again)){
            map.put("errMsg","Passwords are different");
            return "Register";
        }
        User user = new User();
        user.setUsername(name);
        user.setUserpassword(pwd);
        ResultEntity result = userService.Register(user);
        if(result.getSuccess()){
            request.getSession().setAttribute("user",result.getObject());
            request.getSession().setAttribute("visted",result.getObject());
            return "redirect:/profile/like_song";
        }
        else{
            map.put("errMsg",result.getErrorMsg());
            return "Register";
        }
    }
}

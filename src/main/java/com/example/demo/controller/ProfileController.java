package com.example.demo.controller;


import com.example.demo.entity.Singer;
import com.example.demo.entity.Song;
import com.example.demo.entity.SongList;
import com.example.demo.entity.User;
import com.example.demo.entity.result.ResultEntity;
import com.example.demo.service.SingerService;
import com.example.demo.service.SongListService;
import com.example.demo.service.SongService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;
    @Autowired
    private SingerService singerService;

    /**
     * 跳转到个人主页
     */
    @RequestMapping(value = "/profile/like_song", method = RequestMethod.GET)
    public String mainPage(HttpServletRequest request,Map<String, Object> map) {
        ResultEntity e;
        User user = (User) request.getSession(false).getAttribute("visted");
        e = userService.getFans(user);
        ArrayList<User> fans = (ArrayList<User>)e.getObject();
        e= userService.getFriends(user);
        ArrayList<User> friends = (ArrayList<User>)e.getObject();
        ArrayList<Singer> singers = singerService.getSingerUserLike(user.getUserid());
        map.put("FansNum",fans.size());
        map.put("FriendsNum",friends.size()+singers.size());
        return "profile/like_song";
    }

    /**
     * 跳转到个人分析页面
     */
    @RequestMapping(value = "/profile/AnalysisMe", method = RequestMethod.GET)
    public String AnalysisMe(HttpServletRequest request,Map<String, Object> map) {
        ResultEntity e;
        User user = (User) request.getSession(false).getAttribute("visted");
        e = userService.getFans(user);
        ArrayList<User> fans = (ArrayList<User>)e.getObject();
        e= userService.getFriends(user);
        ArrayList<User> friends = (ArrayList<User>)e.getObject();
        ArrayList<Singer> singers = singerService.getSingerUserLike(user.getUserid());
        map.put("FansNum",fans.size());
        map.put("FriendsNum",friends.size()+singers.size());
        return "profile/AnalysisMe";
    }

    /**
     * 跳转到站点分析页面
     */
    @RequestMapping(value = "/profile/AnalysisUs", method = RequestMethod.GET)
    public String AnalysisUs(HttpServletRequest request,Map<String, Object> map) {
        ResultEntity e;
        User user = (User) request.getSession(false).getAttribute("visted");
        e = userService.getFans(user);
        ArrayList<User> fans = (ArrayList<User>)e.getObject();
        e= userService.getFriends(user);
        ArrayList<User> friends = (ArrayList<User>)e.getObject();
        ArrayList<Singer> singers = singerService.getSingerUserLike(user.getUserid());
        map.put("FansNum",fans.size());
        map.put("FriendsNum",friends.size()+singers.size());
        return "profile/AnalysisUs";
    }

    /**
     * 跳转到评论页面
     */
    @RequestMapping(value = "/profile/Recommend", method = RequestMethod.GET)
    public String Recommend(HttpServletRequest request,Map<String, Object> map) {
        ResultEntity e;
        User user = (User) request.getSession(false).getAttribute("visted");
        e = userService.getFans(user);
        ArrayList<User> fans = (ArrayList<User>)e.getObject();
        e= userService.getFriends(user);
        ArrayList<User> friends = (ArrayList<User>)e.getObject();
        ArrayList<Singer> singers = singerService.getSingerUserLike(user.getUserid());
        map.put("FansNum",fans.size());
        map.put("FriendsNum",friends.size()+singers.size());
        return "profile/Recommend";
    }
}

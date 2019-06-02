package com.example.demo.userInterface;

import com.example.demo.entity.Song;
import com.example.demo.entity.SongList;
import com.example.demo.entity.User;
import com.example.demo.entity.result.ResultEntity;
import com.example.demo.service.SongListService;
import com.example.demo.service.SongService;
import com.example.demo.service.UserService;
import com.example.demo.util.Util;
import com.example.demo.util.operation.IOperationUtil;
import com.example.demo.util.showUtil.ShowUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserInterface {

    @Autowired
    private UserService userService;
    @Resource(name = "ShowUtilForWeb")
    private ShowUtil showUtil;
    @Resource(name = "OperationUtil")
    private IOperationUtil operationUtil;

    @RequestMapping(value = "/profile/showFans", method = RequestMethod.GET)
    public ModelAndView showFans(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession(false).getAttribute("visted");
        User my =(User) request.getSession(false).getAttribute("user");
        Map<String,Object> map ;
        ResultEntity e = userService.getFans(user);
        ArrayList<User> Follows = (ArrayList<User>)e.getObject();
        //"Follows"关注的用户 "FollowNum"关注的人数 "isFollow"是否关注
        map = showUtil.showFollow(my.getUserid(),Follows);
        return new ModelAndView("temp/fans_main",map);
    }

    @RequestMapping(value = "/profile/showFollowUser", method = RequestMethod.GET)
    public ModelAndView showFollowUser(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession(false).getAttribute("visted");
        User my =(User) request.getSession(false).getAttribute("user");
        String flag = request.getParameter("flag");
        ResultEntity e = userService.getFriends(user);
        ArrayList<User> Follows = (ArrayList<User>)e.getObject();
        //"Follows"关注的用户 "FollowNum"关注的人数 "isFollow"是否关注
        Map<String,Object> map = showUtil.showFollow(my.getUserid(),Follows);
        if(flag.equals("2"))
            return new ModelAndView("temp/follows/follow_user",map);
        return new ModelAndView("temp/follow_main",map);
    }

    @RequestMapping(value = "/profile/changeFollow", method = RequestMethod.GET)
    public Map<String, String> changeFollow(HttpServletRequest request){
        User my =(User) request.getSession(false).getAttribute("user");
        String id = request.getParameter("id");
        return operationUtil.changeFollow(my.getUserid(),id);
    }
}

package com.example.demo.userInterface;

import com.example.demo.entity.Singer;
import com.example.demo.entity.User;
import com.example.demo.service.SingerService;
import com.example.demo.util.operation.IOperationUtil;
import com.example.demo.util.showUtil.ShowUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;

@RestController
public class SingerInterface {
    @Resource(name = "ShowUtilForWeb")
    private ShowUtil showUtil;
    @Autowired
    private SingerService singerService;
    @Resource(name = "OperationUtil")
    private IOperationUtil operationUtil;

    @RequestMapping(value = "/profile/showFollowSinger", method = RequestMethod.GET)
    public ModelAndView showFollowSinger(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession(false).getAttribute("visted");
        User my =(User) request.getSession(false).getAttribute("user");
        ArrayList<Singer> Follows = (ArrayList<Singer>)singerService.getSingerUserLike(user.getUserid());
        Map<String,Object> map  = showUtil.showSingerFollow(my.getUserid(),Follows);
        return new ModelAndView("temp/follows/follow_singer",map);
    }

    @RequestMapping(value = "/profile/changeFollowSinger", method = RequestMethod.GET)
    public Map<String, String> changeFollowSinger(HttpServletRequest request){
        User my =(User) request.getSession(false).getAttribute("user");
        String id = request.getParameter("id");
        return operationUtil.changeFollowSinger(my.getUserid(),id);
    }
}

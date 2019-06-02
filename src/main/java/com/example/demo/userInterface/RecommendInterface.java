package com.example.demo.userInterface;

import com.example.demo.entity.Song;
import com.example.demo.entity.SongList;
import com.example.demo.entity.User;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RecommendInterface {

    @Autowired
    private UserService userService;
    @Autowired
    private SongService songService;
    @Autowired
    private SongListService songListService;
    @Resource(name = "ShowUtilForWeb")
    private ShowUtil showUtil;

    @RequestMapping(value = "/profile/analysisMyStyle", method = RequestMethod.GET)
    public ModelAndView analysisMyStyle(HttpServletRequest request, @Param("flag")int flag){
        Map<String,Object> map = new HashMap<>();
        if(flag==1){
            map.put("electric", Util.double2Percent(userService.getPercent("电子")));
            map.put("pop",Util.double2Percent(userService.getPercent("流行")));
            map.put("classic",Util.double2Percent(userService.getPercent("古典")));
            map.put("hap",Util.double2Percent(userService.getPercent("嘻哈")));
            map.put("rap",Util.double2Percent(userService.getPercent("摇滚")));
        }
        if(flag==2){
            map.put("electric", Util.double2Percent(songService.getSiteTypePercent("电子")));
            map.put("pop",Util.double2Percent(songService.getSiteTypePercent("流行")));
            map.put("classic",Util.double2Percent(songService.getSiteTypePercent("古典")));
            map.put("hap",Util.double2Percent(songService.getSiteTypePercent("嘻哈")));
            map.put("rap",Util.double2Percent(songService.getSiteTypePercent("摇滚")));
        }
        return new ModelAndView("temp/analysis/myStyle",map);
    }

    @RequestMapping(value = "/profile/analysisMyLanguage", method = RequestMethod.GET)
    public ModelAndView analysisMyLanguage(HttpServletRequest request,@Param("flag")int flag){
        Map<String,Object> map = new HashMap<>();
        if(flag==1){
            map.put("English", Util.double2Percent(userService.getPercent("英语")));
            map.put("Chinese",Util.double2Percent(userService.getPercent("中文")));
            map.put("Japanese",Util.double2Percent(userService.getPercent("日语")));
        }
        if(flag==2){
            map.put("English", Util.double2Percent(songService.getSiteTypePercent("英语")));
            map.put("Chinese",Util.double2Percent(songService.getSiteTypePercent("中文")));
            map.put("Japanese",Util.double2Percent(songService.getSiteTypePercent("日语")));
        }
        return new ModelAndView("temp/analysis/myLanguage",map);
    }

    @RequestMapping(value = "/profile/recommendSong", method = RequestMethod.GET)
    public ModelAndView recommendSong(HttpServletRequest request){
        User user = (User) request.getSession(false).getAttribute("user");
        ArrayList<Song> songs = songService.getCommandSong(user.getUserid());
        Map<String,Object> map = showUtil.showSong(songs);
        List<Integer> point = songService.getPushSongPoint(user.getUserid());
        map.put("point",point);
        return new ModelAndView("temp/recommend/recommendSong",map);
    }

    @RequestMapping(value = "/profile/recommendSongList", method = RequestMethod.GET)
    public ModelAndView recommendSongList(HttpServletRequest request){
        User user = (User) request.getSession(false).getAttribute("user");
        ArrayList<SongList>songList = songListService.getCommandSongList(user.getUserid());
        Map<String,Object> map = showUtil.showSongList(songList);
        return new ModelAndView("temp/recommend/recommendSongList",map);
    }

    @RequestMapping(value = "/profile/recommendUser", method = RequestMethod.GET)
    public ModelAndView recommendUser(HttpServletRequest request){
        User user = (User) request.getSession(false).getAttribute("user");
        ArrayList<User> users = userService.getRecommendUser(user.getUserid());
        Map<String,Object> map = new HashMap<>();
        map.put("Follows",users);
        map.put("point",userService.getFriendPoint(user.getUserid()));
        return new ModelAndView("temp/recommend/recommendUser",map);
    }
}

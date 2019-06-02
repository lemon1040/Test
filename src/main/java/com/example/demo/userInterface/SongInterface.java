package com.example.demo.userInterface;

import com.example.demo.entity.Song;
import com.example.demo.entity.SongList;
import com.example.demo.entity.User;
import com.example.demo.entity.result.ResultEntity;
import com.example.demo.service.KeepService;
import com.example.demo.service.SongListService;
import com.example.demo.service.SongService;
import com.example.demo.service.UserService;
import com.example.demo.util.operation.IOperationUtil;
import com.example.demo.util.showUtil.ShowUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SongInterface {
    @Autowired
    private UserService userService;
    @Autowired
    private SongListService songListService;
    @Resource(name = "ShowUtilForWeb")
    private ShowUtil showUtil;
    @Resource(name = "OperationUtil")
    private IOperationUtil operationUtil;
    @Autowired
    private KeepService keepService;
    @Autowired
    private SongService songService;

    @RequestMapping(value = "/profile/like_song_song_typeList", method = RequestMethod.GET)
    public ModelAndView showFavoriteSong(HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        String flag = request.getParameter("flag");
        ResultEntity e;
        Map<String,Object> map = new HashMap<>();
        User user = new User();
        user.setUserid(id);
        e = userService.getSongLists(user);
        Map<String,Object>e_map = (Map<String, Object>) e.getObject();
        ArrayList<SongList> createdsonglist = (ArrayList<SongList>)e_map.get("createdsonglist");
        getFavoriteList(map,createdsonglist);
        ArrayList<Song> songs = (ArrayList<Song>) map.get("songs");
        map.putAll(showUtil.showSong(songs));
        if(flag.equals("2"))
            return new ModelAndView("temp/mylike/song_list_details",map);
        return new ModelAndView("temp/mylike_main",map);
    }
    @RequestMapping(value = "/favoriteSong",method = RequestMethod.POST)
    public ResultEntity favoriteSong(@Param("songid")String songid, HttpServletRequest request){
        User user = (User) request.getSession(false).getAttribute("user");
        Map<String,Object>map = operationUtil.favoriteSong(user.getUserid(),songid);
        return new ResultEntity((boolean)map.get("succ"),map.get("msg").toString(),null);
    }
    @RequestMapping(value = "/KeepSong",method = RequestMethod.POST)
    public ResultEntity KeepSong(@Param("songlistid")String songlistid, @Param("songid")String songid,
                                 HttpServletRequest request){
        Map<String,Object> map = operationUtil.keepSong(songid,songlistid);
        return new ResultEntity((boolean)map.get("succ"),map.get("msg").toString(),null);
    }
    @RequestMapping(value ="/Comment",method = RequestMethod.POST)
    public String Comment(HttpServletRequest request, @RequestParam("words") String words, @RequestParam("songid") String songid){
        User user = (User) request.getSession(false).getAttribute("user");
        boolean succ = songService.commentSong(words,songid,user.getUserid());
        if(succ)
            return "评论成功";
        else
            return "评论失败";
    }

    private void getFavoriteList(Map<String,Object> map,ArrayList<SongList> createdsonglist){
        ResultEntity e;
        for(SongList i : createdsonglist){
            if(i.getSonglistname().equals("我喜欢")){
                map.put("favoritesonglist",i);
                e = songListService.getSongsInSongList(i);
                map.put("songs",e.getObject());
                break;
            }
        }
    }
}

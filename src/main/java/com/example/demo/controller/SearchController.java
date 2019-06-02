package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.service.AlbumService;
import com.example.demo.service.SongListService;
import com.example.demo.service.SongService;
import com.example.demo.service.UserService;
import com.example.demo.util.showUtil.ShowUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class SearchController {
    @Autowired
    UserService userService;
    @Autowired
    SongService songService;
    @Autowired
    SongListService songListService;
    @Autowired
    AlbumService albumService;
    @Resource(name = "ShowUtilForWeb")
    private ShowUtil showUtil;

    /**
     * 搜索主页面
     */
    @RequestMapping(value ={"/Search","/Search/Song"},method = RequestMethod.GET)
    public String search(@RequestParam("words")String searchWords, Map<String, Object> map, HttpServletRequest request){
        ArrayList<Song>songs = songService.getSongByNamePart(searchWords);
        map.put("words",searchWords);
        map.putAll(showUtil.showSong(songs));
        return "search/search";
    }

    /**
     * 搜索歌单页面
     */
    @RequestMapping(value ="/Search/SongList",method = RequestMethod.GET)
    public String searchSonglist(@RequestParam("words")String searchWords, Map<String, Object> map, HttpServletRequest request){
        ArrayList<SongList> songlist = songListService.getSongListByNamePart(searchWords);
        map.put("words",searchWords);
        map.putAll(showUtil.showSongList(songlist));
        return "search/songlist";
    }

    /**
     * 搜索专辑页面
     */
    @RequestMapping(value ="/Search/Album",method = RequestMethod.GET)
    public String searchAlbum(@RequestParam("words")String searchWords, Map<String, Object> map, HttpServletRequest request){
        ArrayList<Album> albums = albumService.getAlbumByNamePart(searchWords);
        map.put("words",searchWords);
        map.putAll(showUtil.showAlbum(albums));
        return "search/album";
    }

    /**
     * 搜索用户页面
     */
    @RequestMapping(value ="/Search/User",method = RequestMethod.GET)
    public String searchUser(@RequestParam("words")String searchWords, Map<String, Object> map, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        ArrayList<User> users = userService.getUserByNamePart(searchWords);
        map.put("words",searchWords);
        map.putAll(showUtil.showUser(users,user.getUserid()));
        return "search/user";
    }



}

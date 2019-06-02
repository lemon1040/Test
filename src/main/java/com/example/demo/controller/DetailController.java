package com.example.demo.controller;


import com.example.demo.entity.*;
import com.example.demo.entity.result.ResultEntity;
import com.example.demo.service.*;
import com.example.demo.util.showUtil.ShowUtil;
import com.example.demo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class DetailController {

    @Autowired
    private UserService userService;
    @Autowired
    private SongService songService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private SongListService songListService;
    @Resource(name = "ShowUtilForWeb")
    private ShowUtil showUtil;
    @Autowired
    private SingerService singerService;

    /**
     *
     * 跳转到歌曲详情页面
     *"song" 歌曲 "singers"歌曲的所有歌手 "album"歌曲的专辑 "comments"歌曲评论  "commentsUser"评论的发布人
     */
    @RequestMapping(value ="/Song",method = RequestMethod.GET)
    public String songDetail(@RequestParam("songid") String songid, Map<String, Object> map,HttpServletRequest request){
        Song song = songService.getSongById(songid);
        ResultEntity e = songService.getSingersInSong(songid);
        ArrayList<Singer> singers = (ArrayList<Singer>)e.getObject();
        ArrayList<comments> comments = songService.getCommentsInSong(songid);
        ArrayList<User> users = new ArrayList<>();
        for(com.example.demo.entity.comments comment:comments){
            e = userService.getUserById(comment.getUserid());
            users.add((User) e.getObject());
        }
        Album album = albumService.getAlbumByAlbumId(song.getAlbumid());
        map.put("song",song);
        map.put("singers",singers);
        map.put("album",album);
        map.put("comments",comments);
        map.put("commentsUser",users);
        return "Details/song_detail";
    }
    /**
     *
     * 跳转到歌单详情页面
     *"creater" 歌单创建者  "savenum"歌单收藏数 "style"歌单中的歌曲 "songlist"歌单
     */
    @RequestMapping(value ="/SongList",method = RequestMethod.GET)
    public String songListDetail(@RequestParam("songlistid") String songlistid, Map<String, Object> map,HttpServletRequest request){
        SongList songList = songListService.getSongListById(songlistid);
        ResultEntity e = songListService.getSongsInSongList(songList);
        ArrayList<Song>songs = (ArrayList<Song>)e.getObject();
        String style = Util.getSongListStyle(songs);
        map.putAll(showUtil.showSong(songs));
        e = songListService.getSongListSavedNum(songList);
        int num = (int)e.getObject();
        e = userService.getUserById(songList.getUserid());
        User creater = (User)e.getObject();
        map.put("creater",creater);
        map.put("savenum",num);
        map.put("style",style);
        map.put("songlist",songList);
        return "Details/songlist_detail";
        //"songs" "singers" "singername" "albums"
    }
    /**
     *
     * 跳转到歌手详情页面
     */
    @RequestMapping(value ="/Singer",method = RequestMethod.GET)
    public String singerDetail(@RequestParam("singerid") String singerid, Map<String, Object> map,HttpServletRequest request){
        User user = (User) request.getSession(false).getAttribute("user");
        Singer singer = singerService.getSingerById(singerid);
        ArrayList<Song> songs = singerService.getSingerSong(singerid);
        if(songs.size()>5){
            map.put("songs",songs.subList(0,4));
        }else {
            map.put("songs",songs);
        }
        ArrayList<Album> albums = singerService.getSingerAlbum(singerid);
        Boolean isfollow = singerService.isUserLikeSinger(singerid,user.getUserid());
        map.put("isfollow",isfollow);
        int follownum = singerService.getFansNum(singerid);
        ResultEntity e = songListService.getSingerInSongList(songs);
        ArrayList<ArrayList<Singer>> singers = (ArrayList<ArrayList<Singer>>)e.getObject();
        map.put("singers",e.getObject());
        map.put("albums",albums);
        map.put("singer",singer);
        map.put("follownum",follownum);
        return "Details/singer_detail";
       //"songs" "albums" "follownum"
    }

    /**
     *
     * 跳转到专辑详情页面
     */
    @RequestMapping(value ="/Album",method = RequestMethod.GET)
    public String albumDetail(@RequestParam("albumid") String albumid, Map<String, Object> map,HttpServletRequest request){
        Album album = albumService.getAlbumByAlbumId(albumid);
        ArrayList<Song> songs = albumService.getSongsInAlbum(albumid);
        ResultEntity e;
        e = songListService.getSingerInSongList(songs);
        map.put("singers",e.getObject());
        ArrayList<String>singername = Util.unionSingers((ArrayList<ArrayList<Singer>>)e.getObject());
        map.put("singername",singername);
        Singer author = singerService.getSingerById(album.getSingerid());
        String style = Util.getSongListStyle(songs);
        map.put("style",style);
        map.put("author",author);
        map.put("songs",songs);
        map.put("album",album);
        return "Details/album_detail";
    }

    /**
     *
     * 跳转到个人详情页面
     */
    @RequestMapping(value ="/User",method = RequestMethod.GET)
    public String showUser(@RequestParam("userid") String userid, Map<String, Object> map,HttpServletRequest request){
        ResultEntity e = userService.getUserById(userid);
        if(e.getSuccess()){
            request.getSession().setAttribute("visted",e.getObject());
        }
        return "redirect:/profile/like_song";
    }

}

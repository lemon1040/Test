package com.example.demo.service.Impl;

import com.example.demo.dao.SongListMapper;
import com.example.demo.dao.SongMapper;
import com.example.demo.entity.Song;
import com.example.demo.entity.SongList;
import com.example.demo.service.KeepService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
@Service
public class KeepImpl implements KeepService {
    @Resource
    private SongMapper songMapper;
    @Resource
    private SongListMapper songListMapper;

    /**
     * @return {@code 歌单不存在："-4"}
     * <li> {@code 歌曲不存在: "-3"}</li>
     * <li> {@code 输入非法: "-2"}</li>
     * <li> {@code 用户没有操作权限: "-1"}</li>
     * <li> {@code 歌曲已存在: "0"}</li>
     * <li> {@code 保存歌曲成功: "1"}</li>
     */
    public String KeepSong(String songid,String songlistid, String userid){
        if (songid == null || songlistid ==null || userid == null) {
            return "-2";
        }
        Map<String,Object> map = new HashMap<>();
        map.put("songlistid",songlistid);
        map.put("songid",songid);
        songMapper.getSongById(map);
        ArrayList<Song>songs =  (ArrayList<Song>)map.get("songs");
        Song song = songs == null || songs.size() == 0 ? null: songs.get(0);
        if (song == null) {
            return "-3";
        }
        songListMapper.getSongListById(map);
        ArrayList<SongList> songLists = (ArrayList<SongList>)map.get("songlist");
        if (songLists == null ||songLists.size() == 0 ) {
            return "-4";
        }
        SongList songList = songLists.get(0);
        if (!songList.getUserid().equals(userid)) {
            return "-1";
        }
        songListMapper.addSongToSongList(map);
        return (String)map.get("succ");
    }
    
    public String KeepSong(String songid,String songlistid){
        if (songid == null || songlistid ==null) {
            return "-2";
        }
        Map<String,Object> map = new HashMap<>();
        map.put("songlistid",songlistid);
        map.put("songid",songid);
        songListMapper.addSongToSongList(map);
        return (String)map.get("succ");
    }
    public String unKeepSongList(String songlistid,String userid){
        Map<String,Object> map = new HashMap<>();
        map.put("songlistid",songlistid);
        map.put("userid",userid);
        songListMapper.unKeepSongList(map);
        return (String)map.get("succ");
    }

    /**
     * @return {@code 输入非法: "-2"}
     * <li> {@code 用户是歌单创建人无法收藏 "-1"} </li>
     * <li> {@code 歌单不存在 "-3"} </li>
     * <li> {@code 歌单已经收藏了 "0"} </li>
     * <li> {@code 收藏成功 "1"} </li>
     */
    public String KeepSongList(String songlistid,String userid){
        if (songlistid ==null || userid == null) {
            return "-2";
        }
        Map<String,Object> map = new HashMap<>();
        map.put("songlistid",songlistid);
        songListMapper.getSongListById(map);
        ArrayList<SongList> songLists = (ArrayList<SongList>)map.get("songlist");
        SongList songlist = songLists != null && songLists.size() != 0 ? songLists.get(0) : null;
        if (songlist == null) {
            return "-3";
        }
        if (songlist.getUserid().equals(userid)) {
            return "-1";
        }
        map.put("userid",userid);
        songListMapper.keepSongList(map);
        return (String)map.get("succ");
    }

    public String unKeepSong(String songid,String songlistid){
        Map<String,Object> map = new HashMap<>();
        map.put("songlistid",songlistid);
        map.put("songid",songid);
        songListMapper.deleteSongFromSongList(map);
        return (String)map.get("succ");
    }
}

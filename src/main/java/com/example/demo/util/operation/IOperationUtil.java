package com.example.demo.util.operation;

import java.util.Map;

public interface IOperationUtil {
    Map<String,String> changeFollow(String userid, String friendid);
    Map<String,String> changeFollowSinger(String userid,String singerid);
    Map<String,Object> keepSongList(String userid,String songlistid);
    Map<String,Object> changeSongListName(String songlistid,String name);
    Map<String,Object> keepSong(String songid,String songlistid);
    Map<String,Object> favoriteSong(String userid,String songid);
}

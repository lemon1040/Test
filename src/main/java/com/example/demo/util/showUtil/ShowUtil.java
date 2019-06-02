package com.example.demo.util.showUtil;

import com.example.demo.entity.*;

import java.util.ArrayList;
import java.util.Map;

public interface ShowUtil {
    Map<String,Object> showSong(ArrayList<Song> songs);
    Map<String,Object> showSongList(ArrayList<SongList> songLists);
    Map<String,Object> showAlbum(ArrayList<Album> albums);
    Map<String,Object> showFollow(String id, ArrayList<User> follows);
    Map<String, Object> showUser(ArrayList<User> users, String myid);
    Map<String,Object> showSingerFollow(String id, ArrayList<Singer> follows);
}

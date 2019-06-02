package com.example.demo.service;

import com.example.demo.entity.SongList;
import com.example.demo.entity.User;
import com.example.demo.entity.result.ResultEntity;

import java.util.ArrayList;
import java.util.List;

public interface UserService {
    ResultEntity SignIn(User user);
    ResultEntity Register(User user);
    ResultEntity getFans(User user);
    ResultEntity followUser(String userid, String friendid);
    ResultEntity unFollowUser(String userid, String friendid);
    ResultEntity getFriends(User user);
    ResultEntity getFollowSingers(User user);
    ResultEntity getSongLists(User user);
    ArrayList<SongList> getCreatedSongList(String userid);
    ArrayList<SongList> getKeepedSongList(String userid);
    ArrayList<User> getUserByNamePart(String username);
    ResultEntity getUserById(String id);
    ResultEntity isFriendExist(String userid,String friendid);
    SongList getFavoritelist(String userid);
    ArrayList<User> getRecommendUser(String userid);
    List<Integer> getFriendPoint(String userid);
    double getPercent(String type);

}

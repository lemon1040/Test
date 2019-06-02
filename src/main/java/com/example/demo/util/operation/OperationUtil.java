package com.example.demo.util.operation;

import com.example.demo.entity.SongList;
import com.example.demo.entity.result.ResultEntity;
import com.example.demo.service.KeepService;
import com.example.demo.service.SingerService;
import com.example.demo.service.SongListService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component(value = "OperationUtil")
public class OperationUtil implements IOperationUtil {

    @Autowired
    private SingerService singerService;
    @Autowired
    private UserService userService;
    @Autowired
    private KeepService keepService;
    @Autowired
    private SongListService songListService;
    /**
     *
     * @param userid
     * @param friendid
     * @return "succ" "flag"
     */
    public Map<String,String> changeFollow(String userid, String friendid){
        //先判断用户关注这个人没有
        Map<String,String> map = new HashMap<>();
        ResultEntity e = userService.isFriendExist(userid,friendid);
        boolean is = (boolean)e.getObject();
        //如果关注了就取消关注
        if(is){
            map.put("flag","2");
            e = userService.unFollowUser(userid,friendid);
        }
        //没有关注就关注
        else{
            map.put("flag","1");
            e = userService.followUser(userid,friendid);
        }
        if((boolean)e.getObject())
            map.put("succ","1");
        else
            map.put("succ","0");
        return map;
    }

    public Map<String,String> changeFollowSinger(String userid,String singerid){
        //先判断用户关注这个人没有
        Map<String,String> map = new HashMap<>();
        boolean is = singerService.isUserLikeSinger(singerid,userid);
        //如果关注了就取消关注
        if(is){
            map.put("flag","2");
            is = singerService.unfollowSinger(userid,singerid);
        }
        //没有关注就关注
        else{
            map.put("flag","1");
            is = singerService.followSinger(userid,singerid);
        }
        if(is)
            map.put("succ","1");
        else
            map.put("succ","0");
        return map;
    }
    public Map<String,Object> changeSongListName(String songlistid,String name){
        String succ = songListService.changeSongListName(name,songlistid);
        Map<String,Object>map = new HashMap<>();
        if(succ.equals("1")){
            map.put("msg","修改名字成功了嗷");
            map.put("succ",true);
        }
        else{
            map.put("msg","修改失败了嗷");
            map.put("succ",false);
        }

        return map;
    }
    public Map<String,Object> keepSongList(String userid,String songlistid){
        Map<String,Object > map = new HashMap<>();
        String result = keepService.KeepSongList(songlistid,userid);
        if(result.equals("0")){
            keepService.unKeepSongList(songlistid,userid);
            map.put("msg","歌单已取消收藏");
            map.put("succ",false);
        }
        else{
            map.put("msg","收藏成功");
            map.put("succ",true);
        }
        return map;
    }
    public Map<String,Object> keepSong(String songid,String songlistid){
        Map<String,Object > map = new HashMap<>();
        String result = keepService.KeepSong(songid,songlistid);
        if(result.equals("0")){
            map.put("msg","歌曲已存在");
            map.put("succ",false);
        }
        else{
            map.put("msg","已添加到歌单");
            map.put("succ",true);
        }
        return map;
    }

    public Map<String,Object> favoriteSong(String userid,String songid){
        Map<String,Object > map = new HashMap<>();
        SongList favorite = userService.getFavoritelist(userid);
        if(favorite == null){
            map.put("msg","用户默认歌单不存在");
            map.put("succ",false);
            return map;
        }
        String result = keepService.KeepSong(songid,favorite.getSonglistid());
        if(result.equals("0")){
            keepService.unKeepSong(songid,favorite.getSonglistid());
            map.put("msg","取消喜欢成功");
            map.put("succ",true);
            return map;
        }
        map.put("msg","喜欢成功");
        map.put("succ",true);
        return map;
    }
}

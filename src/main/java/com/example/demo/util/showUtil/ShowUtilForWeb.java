package com.example.demo.util.showUtil;

import com.example.demo.entity.*;
import com.example.demo.entity.result.ResultEntity;
import com.example.demo.service.AlbumService;
import com.example.demo.service.SingerService;
import com.example.demo.service.SongListService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component(value = "ShowUtilForWeb")
public class ShowUtilForWeb implements ShowUtil {
    @Autowired
    private UserService userService;
    @Autowired
    private SongListService songListService;
    @Autowired
    private SingerService singerService;
    @Autowired
    private AlbumService albumService;

    /**
     * @param :Arraylist<song> songs
     * @out :"songs" "singers"  "albums"
     */
    @Override
    public Map<String,Object> showSong(ArrayList<Song> songs){
        Map<String,Object> map = new HashMap<>();
        map.put("songs",songs);
        ResultEntity e;
        e = songListService.getSingerInSongList(songs);
        map.put("singers",e.getObject());
        e = songListService.getAlbumsInSongList(songs);
        map.put("albums",e.getObject());
        return map;
    }
    /**
     * @param :Arraylist<song> songs
     * @out : "songlist""songnum" "savenum"  "user"
     */
    @Override
    public Map<String,Object> showSongList(ArrayList<SongList> songLists){
        Map<String,Object> map = new HashMap<>();
        ArrayList<Integer>songnum = new ArrayList<>();
        ArrayList<Integer>savenum = new ArrayList<>();
        ArrayList<User>users = new ArrayList<>();
        for(SongList list : songLists){
            ArrayList<Song> l = (ArrayList<Song>)songListService.getSongsInSongList(list).getObject();
            songnum.add(l.size());
            savenum.add((int)songListService.getSongListSavedNum(list).getObject());
            User user = (User) userService.getUserById(list.getUserid()).getObject();
            users.add(user);
        }
        map.put("songlist",songLists);
        map.put("songnum",songnum);
        map.put("savenum",savenum);
        map.put("users",users);
        return map;
    }

    /**
     *
     * @param albums
     * @return "albums" "songnum" "singers"
     */
    @Override
    public Map<String,Object> showAlbum(ArrayList<Album> albums){
        Map<String,Object> map = new HashMap<>();
        ArrayList<Integer> songnum = new ArrayList<>();
        ArrayList<Singer> singers = new ArrayList<>();
        for(Album album:albums){
            Singer singer = singerService.getSingerById(album.getSingerid());
            ArrayList<Song> songs = albumService.getSongsInAlbum(album.getAlbumid());
            singers.add(singer);
            songnum.add(songs.size());
        }
        map.put("albums",albums);
        map.put("songnum",songnum);
        map.put("singers",singers);
        return map;
    }
    /**
     *
     * @param id
     * @param follows
     * @return "Follows"关注的用户 "FollowNum"关注的人数 "isFollow"是否关注
     */
    @Override
    public Map<String,Object> showFollow(String id, ArrayList<User> follows){
        Map<String,Object> map = new HashMap<>();
        ArrayList<Integer> FollowNum = new ArrayList<>();
        ArrayList<Boolean> isFollow = new ArrayList<>();
        for(User user:follows){
            ArrayList<User> users = (ArrayList<User>)userService.getFans(user).getObject();
            FollowNum.add(users.size());
            boolean i = (boolean)userService.isFriendExist(id,user.getUserid()).getObject();
            isFollow.add(i);
        }
        map.put("Follows",follows);
        map.put("FollowNum",FollowNum);
        map.put("isFollow",isFollow);
        return map;
    }

    /**
     *
     * @param users
     * @return "songlistnum" "fansnum" "isfollowed" "users"
     */
    @Override
    public Map<String, Object> showUser(ArrayList<User> users, String myid){
        ArrayList<Integer> songlistnum = new ArrayList<>();
        ArrayList<Integer> fansnum = new ArrayList<>();
        ArrayList<Boolean> isfollowed = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        for(User user:users){
            isfollowed.add((Boolean)userService.isFriendExist(myid,user.getUserid()).getObject());
            ArrayList<User> i = (ArrayList<User>)userService.getFans(user).getObject();
            fansnum.add(i.size());
            songlistnum.add(userService.getCreatedSongList(user.getUserid()).size());
        }
        map.put("songlistnum",songlistnum);
        map.put("fansnum",fansnum);
        map.put("isfollowed",isfollowed);
        map.put("users",users);
        return map;
    }

    @Override
    public Map<String,Object> showSingerFollow(String id, ArrayList<Singer> follows){
        Map<String,Object> map = new HashMap<>();
        ArrayList<Integer> FollowNum = new ArrayList<>();
        ArrayList<Boolean> isFollow = new ArrayList<>();
        for(Singer singer:follows){
            FollowNum.add(singerService.getFansNum(singer.getSingerid()));
            boolean i = singerService.isUserLikeSinger(singer.getSingerid(),id);
            isFollow.add(i);
        }
        map.put("Follows",follows);
        map.put("FollowNum",FollowNum);
        map.put("isFollow",isFollow);
        return map;
    }







}

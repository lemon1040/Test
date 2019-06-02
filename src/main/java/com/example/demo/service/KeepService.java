package com.example.demo.service;

public interface KeepService {
    /**
     * @return {@code 歌单不存在："-4"}
     * <li> {@code 歌曲不存在: "-3"}</li>
     * <li> {@code 输入非法: "-2"}</li>
     * <li> {@code 用户没有操作权限: "-1"}</li>
     * <li> {@code 数据库错误: "0"}</li>
     * <li> {@code 保存歌曲成功: "1"}</li>
     */
    String KeepSong(String songid,String songlistid, String userid);
    String KeepSong(String songid,String songlistid);

    /**
     * @return {@code 输入非法: "-2"}
     * <li> {@code 用户是歌单创建人无法收藏 "-1"} </li>
     * <li> {@code 歌单不存在 "-3"} </li>
     * <li> {@code 歌单已经收藏了 "0"} </li>
     * <li> {@code 收藏成功 "1"} </li>
     */
    String KeepSongList(String songlistid,String userid);

    String unKeepSongList(String songlistid,String userid);
    
    String unKeepSong(String songid,String songlistid);
}

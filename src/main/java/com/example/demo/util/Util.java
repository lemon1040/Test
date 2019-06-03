package com.example.demo.util;

import com.example.demo.entity.Singer;
import com.example.demo.entity.Song;

import java.util.*;

public  class  Util {
    public static String getSongListStyle(ArrayList<Song> songs) {
        if (songs == null || songs.size() == 0) {
            return " ";
        }
        class Group {
            String index;
            int num;
        }
        class Sort implements Comparator {
            public int compare(Object o1, Object o2) {
                Group s1 = (Group) o1;
                Group s2 = (Group) o2;
                if (s1.num > s2.num)
                    return -1;
                return 1;
            }
        }
        Map<String, Group> resultMap = new LinkedHashMap<>();
        int countIndex = 0;
        for(Song song:songs){
            //如果这个值运算过 不再运算
            if (resultMap.get(song.getSongschool()) != null) {
                countIndex++;
                continue;
            }
            Group group = new Group();
            group.index = song.getSongschool();
            for (int i = countIndex; i < songs.size(); i++) {
                if (songs.get(i).getSongschool().equals(group.index)) {
                    group.num++;
                }
            }
            resultMap.put(group.index, group);
            countIndex++;
        }
        ArrayList<Group> total = new ArrayList<>(resultMap.values());
        Collections.sort(total, new Sort());
        int i = 0;
        StringBuilder sb = new StringBuilder();
        for(Group cur:total){
            if(i>2){
                break;
            }
            sb.append(cur.index);
            sb.append(" ");
            i++;
        }
        return sb.toString();
    }

    public static ArrayList<String> unionSingers(ArrayList<ArrayList<Singer>> singer){
        ArrayList<String>singername = new ArrayList<>(singer.size());
        for(ArrayList<Singer> arr:singer){
            StringBuilder sb = new StringBuilder();
            for(Singer i:arr){
                sb.append(i.getSingername());
                sb.append(", ");
            }
            singername.add(sb.toString());
        }
        return singername;
    }
    public static String double2Percent(double i){
        Integer j = (int) (100*i);
        return j.toString()+"%";
    }
}

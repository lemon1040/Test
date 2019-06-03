package com.example.demo.userInterface;

import com.example.demo.util.Util;

import java.util.ArrayList;

import com.example.demo.DemoApplication;
import com.example.demo.entity.Song;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;




@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@Transactional
public class slGetMainStyleOfSongListTest {
    
    @Test
    public void nullSongList() throws Exception {
        ArrayList<Song> songs = null;
        String result = Util.getSongListStyle(songs);
        Assert.assertEquals(" ", result);
    }

    @Test
    public void noMoreThan2() throws Exception {
        ArrayList<Song> songs = new ArrayList<>();
        Song song = new Song();
        song.setSongschool("温柔");
        songs.add(song);
        song = new Song();
        song.setSongschool("欢快");
        songs.add(song);
        String result = Util.getSongListStyle(songs);
        Assert.assertEquals("温柔 欢快 ", result);
    }

    @Test
    public void moreThan2() throws Exception {
        ArrayList<Song> songs = new ArrayList<>();
        Song song = new Song();
        song.setSongschool("温柔");
        songs.add(song);
        song = new Song();
        song.setSongschool("欢快");
        songs.add(song);
        song = new Song();
        song.setSongschool("欢快");
        songs.add(song);
        song = new Song();
        song.setSongschool("幸福");
        songs.add(song);
        song = new Song();
        song.setSongschool("爵士");
        songs.add(song);
        song = new Song();
        song.setSongschool("摇滚");
        songs.add(song);
        String result = Util.getSongListStyle(songs);
        Assert.assertEquals("欢快 温柔 幸福 ", result);
    }

    @Test
    public void withRepetition() throws Exception {
        ArrayList<Song> songs = new ArrayList<>();
        for (int i=0; i< 3; i++) {
            Song song = new Song();
            song.setSongschool("温柔");
            songs.add(song);
        }
        Song song = new Song();
        song.setSongschool("欢快");
        songs.add(song);
        String result = Util.getSongListStyle(songs);
        Assert.assertEquals("温柔 欢快 ", result);
    }

    
}
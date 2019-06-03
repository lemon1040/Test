package com.example.demo.userInterface;
import com.example.demo.DemoApplication;
import com.example.demo.service.SongListService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;




@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@Transactional
public class slDeleteSongListTest {
    @Autowired
    private SongListService songListService;
    
    @Test
    public void illegalInput() throws Exception {
        String result = songListService.deleteSongList(null, null);
        Assert.assertEquals("-3", result);
    }

    @Test
    public void wrongSonglistid() throws Exception {
        String result = songListService.deleteSongList("1205555555555", "100001");
        Assert.assertEquals("-2", result);
    }

    @Test
    public void wrongUserid() throws Exception {
        String result = songListService.deleteSongList("3000509", "100001");
        Assert.assertEquals("-1", result);
    }

    @Test
    public void standardInput() throws Exception {
        String result = songListService.deleteSongList("3000536", "100001");
        Assert.assertEquals("1", result);
    }

    
}
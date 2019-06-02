package com.example.demo.userInterface;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import javax.servlet.ServletContext;

import com.alibaba.fastjson.JSON;
import com.example.demo.DemoApplication;
import com.example.demo.service.SongListService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;




@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@Transactional
public class slCreateNewSongListTest {
    @Autowired
    private SongListService songListService;
    
    @Test
    public void notLogin() throws Exception {
        String result = songListService.createNewSongList("name", "image", "1", null);
        Assert.assertEquals(true, result == null);
    }

    @Test
    public void illegalInput() throws Exception {
        String result = songListService.createNewSongList(null, "image", "1", "100001");
        Assert.assertEquals(true, result == null);
    }

    @Test
    public void legalInput() throws Exception {
        String result = songListService.createNewSongList("name", "image", "1", "100001");
        Assert.assertEquals(false, result == null);
    }

    
}
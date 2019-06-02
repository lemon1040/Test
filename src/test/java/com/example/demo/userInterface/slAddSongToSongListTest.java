package com.example.demo.userInterface;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;


import com.example.demo.DemoApplication;
import com.example.demo.service.KeepService;

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
public class slAddSongToSongListTest {
    @Autowired
    private KeepService keepService;
    
    @Test
    public void standardInput() throws Exception {
        String result = keepService.KeepSong("4000007", "3000537", "100001");
        Assert.assertThat(result, anyOf(is("0"), is("1")));
    }

    @Test
    public void illegalInput() throws Exception {
        String result = keepService.KeepSong(null, null, null);
        Assert.assertEquals("-2", result);
    }

    @Test
    public void illegalSongid() throws Exception {
        String result = keepService.KeepSong("1111111111111", "3000537", "100001");
        Assert.assertEquals("-3", result);
    }

    @Test
    public void illegalSongListId() throws Exception {
        String result = keepService.KeepSong("4000007", "1111111111111111111", "100001");
        Assert.assertEquals("-4", result);
    }

    @Test
    public void useridNotMatchSongListId() throws Exception {
        String result = keepService.KeepSong("4000007", "3000537", "100002");
        Assert.assertEquals("-1", result);
    }

    
}
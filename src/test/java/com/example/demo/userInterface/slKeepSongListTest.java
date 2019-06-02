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
public class slKeepSongListTest {
    @Autowired
    private KeepService keepService;
    
    @Test
    public void standardInput() throws Exception {
        String result = keepService.KeepSongList("3000522", "100001");
        Assert.assertThat(result, anyOf(is("0"), is("1")));
    }

    @Test
    public void illegalInput() throws Exception {
        String result = keepService.KeepSongList(null, null);
        Assert.assertEquals("-2", result);
    }

    @Test
    public void illegalSongListid() throws Exception {
        String result = keepService.KeepSongList("1111111111111", "100001");
        Assert.assertEquals("-3", result);
    }

    @Test
    public void useridNotMatchSongListId() throws Exception {
        String result = keepService.KeepSongList("1555555", "100001");
        Assert.assertEquals("-1", result);
    }

    
}
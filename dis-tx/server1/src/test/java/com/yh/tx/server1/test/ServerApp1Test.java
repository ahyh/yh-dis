package com.yh.tx.server1.test;

import com.yh.tx.server1.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServerApp1Test {

    @Autowired
    private UserService userService;

    @Test
    public void test() {
        int i = userService.insertAndUpdate("aaaa.bbb@163.com");
        System.out.println(i);
    }
}

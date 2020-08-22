package com.yh.tx.server2.test;

import com.yh.tx.server2.dao.SalaryMapper;
import com.yh.tx.server2.entity.Salary;
import com.yh.tx.server2.service.SalaryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServerApp2Test {

//    @Autowired
    private SalaryService salaryService;

    @Autowired
    private SalaryMapper salaryMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void test() {
        Salary salary = new Salary();
        salary.setId(30L);
        salary.setSalary(new BigDecimal("899.9"));
        int update = salaryService.update(salary);
        System.out.println(update);
    }
}

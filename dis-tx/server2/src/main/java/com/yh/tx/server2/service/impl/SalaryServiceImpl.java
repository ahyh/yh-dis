package com.yh.tx.server2.service.impl;

import com.yh.global.tx.annotations.GlobalTransaction;
import com.yh.global.tx.util.MyRestTemplate;
import com.yh.tx.server2.dao.SalaryMapper;
import com.yh.tx.server2.entity.Salary;
import com.yh.tx.server2.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Component
public class SalaryServiceImpl implements SalaryService {

    @Autowired
    private SalaryMapper salaryMapper;

    @Autowired
    private MyRestTemplate restTemplate;

    @GlobalTransaction(isStart = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int update(Salary salary) {
        Integer update = salaryMapper.update(salary);
        String forObject = restTemplate.get("http://localhost:8091/user/update/qqaqq.yyyas@sina.com");
        System.out.println(forObject);
        int j = 100 / 0;
        return update;
    }
}

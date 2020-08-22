package com.yh.tx.server2.dao;


import com.yh.tx.server2.entity.Salary;

import java.util.List;

/**
 * SalaryMapper
 *
 * @author yanhuan
 */
public interface SalaryMapper {

    Integer insert(Salary salary);

    Integer update(Salary salary);

    Integer delete(Long id);

}

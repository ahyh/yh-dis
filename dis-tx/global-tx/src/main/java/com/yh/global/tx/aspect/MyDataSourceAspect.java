package com.yh.global.tx.aspect;

import com.yh.global.tx.connection.MyConnection;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.sql.Connection;

@Aspect
@Component
public class MyDataSourceAspect {

    @Around("execution(* javax.sql.DataSource.getConnection(..))")
    public Connection around(ProceedingJoinPoint point) throws Throwable {
        Connection connection = (Connection) point.proceed();
        // 通过DataSource获取到的连接包装成MyConnection, 在MyConnection的commit方法中有全局事务控制的逻辑
        return new MyConnection(connection);
    }
}

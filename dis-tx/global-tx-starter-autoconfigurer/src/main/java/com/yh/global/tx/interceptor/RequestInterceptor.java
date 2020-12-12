package com.yh.global.tx.interceptor;

import com.yh.global.tx.manager.GlobalTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String groupId = request.getHeader("groupId");
        GlobalTransactionManager.setCurrentGroupId(groupId);
        return true;
    }
}

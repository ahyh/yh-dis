package com.yh.global.tx.util;

import com.yh.global.tx.manager.GlobalTransactionManager;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class MyRestTemplate {

    public static String get(String url) {
        String result = "";
        try {
            RestTemplate restTemplate = new RestTemplate();
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-type", "application/json");
            headers.put("groupId", GlobalTransactionManager.getCurrentGroupId());
            result = restTemplate.getForObject(url, String.class, headers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

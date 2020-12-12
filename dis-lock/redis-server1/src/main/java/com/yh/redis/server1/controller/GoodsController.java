package com.yh.redis.server1.controller;

import com.yh.redis.server1.constants.Constants;
import com.yh.redis.server1.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class GoodsController {

    private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping(value = "/get/{key}")
    public String getByKey(@PathVariable("key") String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @GetMapping("/buy_goods")
    public String buyGoods() throws Exception {
        // 放在redis中的值，用于后面的删除分布式锁的时候区分是不是自己加的锁，如果不是则不能再本次线程中删除
        String value = UUID.randomUUID().toString() + Thread.currentThread().getName();
        try {
            // 原子加锁操作
            Boolean flag = redisTemplate.opsForValue().setIfAbsent(Constants.BUY_GOODS_REDIS_DIST_LOCK_KEY + "goods001", value, 10L, TimeUnit.SECONDS);
            if (flag != null && !flag) {
                // 如果加锁失败，则说明分布式锁被其他线程占用
                return "get dist lock failed";
            }
            // 业务逻辑
            String result = redisTemplate.opsForValue().get("goods:001");
            int num = result == null ? 0 : Integer.parseInt(result);
            if (num > 0) {
                int goodsNum = num - 1;
                redisTemplate.opsForValue().set("goods:001", String.valueOf(goodsNum));
                logger.info("buy success, stock remain:{}, server port:{}", goodsNum, serverPort);
                return "buy success, stock remain:" + goodsNum + " , server port:" + serverPort;
            } else {
                logger.info("buy failed, server port:{}", serverPort);
                return "buy failed, server port:" + serverPort;
            }
        } catch (Exception e) {
            logger.error("buy goods failed, e:{}", e.getMessage());
            return "buy goods failed";
        } finally {
            // 使用Lua脚本来删除分布式锁
            Jedis jedis = RedisUtils.getJedis();
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then" +
                    " return redis.call('del', KEYS[1])" +
                    " else" +
                    " return 0" +
                    " end";
            try {
                Object obj = jedis.eval(script, Collections.singletonList(Constants.BUY_GOODS_REDIS_DIST_LOCK_KEY + "goods001"), Collections.singletonList(value));
                if (obj != null && "1".equals(obj.toString())) {
                    logger.info("delete redis lock success");
                } else {
                    logger.info("delete redis lock failed");
                }
            } finally {
                if (null != jedis) {
                    jedis.close();
                }
            }
        }
    }
}

package com.yh.redis.server2.redisson;

import com.yh.redis.server2.constants.Constants;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 使用Redisson实现的分布式锁
 *
 * @author yanhuan
 */
@RestController
public class GoodsController2 {

    private static final Logger logger = LoggerFactory.getLogger(GoodsController2.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private Redisson redisson;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping(value = "/get/{key}")
    public String getByKey(@PathVariable("key") String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @GetMapping("redisson/buy_goods")
    public String buyGoods() throws Exception {
        // 放在redis中的值，用于后面的删除分布式锁的时候区分是不是自己加的锁，如果不是则不能再本次线程中删除
        String value = UUID.randomUUID().toString() + Thread.currentThread().getName();
        RLock lock = redisson.getLock(Constants.BUY_GOODS_REDIS_DIST_LOCK_KEY_REDISSON + "goods001");
        lock.lock();
        try {
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
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}

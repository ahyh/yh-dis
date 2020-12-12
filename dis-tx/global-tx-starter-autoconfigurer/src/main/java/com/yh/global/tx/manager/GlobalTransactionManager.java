package com.yh.global.tx.manager;

import com.alibaba.fastjson.JSONObject;
import com.yh.global.tx.enums.CommandType;
import com.yh.global.tx.netty.NettyClient;
import com.yh.global.tx.transaction.MyTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 全局事务管理者
 */
public class GlobalTransactionManager {

    private static NettyClient nettyClient;

    private static ThreadLocal<MyTransaction> current = new ThreadLocal<>();
    private static ThreadLocal<String> currentGroupId = new ThreadLocal<>();

    @Autowired
    public void setNettyClient(NettyClient nettyClient) {
        GlobalTransactionManager.nettyClient = nettyClient;
    }

    public static Map<String, MyTransaction> transactionMap = new HashMap<>();

    /**
     * 创建事务组
     *
     * @return
     */
    public static String createGroup() {
        if (currentGroupId.get() != null) {
            return currentGroupId.get();
        }
        String groupId = UUID.randomUUID().toString();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("groupId", groupId);
        jsonObject.put("command", CommandType.create.name());
        nettyClient.send(jsonObject);
        currentGroupId.set(groupId);
        System.out.println("创建事务组");
        return groupId;
    }

    /**
     * 创建分支事务
     *
     * @param groupId
     * @return
     */
    public static MyTransaction createBranchTransaction(String groupId) {
        String transactionId = UUID.randomUUID().toString();
        MyTransaction transaction = new MyTransaction(transactionId, groupId);
        transactionMap.put(groupId, transaction);
        current.set(transaction);

        System.out.println("创建分支事务");
        return transaction;
    }

    /**
     * 注册分支事务
     *
     * @param transaction
     * @return
     */
    public static void registryBranchTransaction(MyTransaction transaction) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("command", CommandType.registry.name());
        jsonObject.put("groupId", transaction.getGroupId());
        jsonObject.put("transactionId", transaction.getTransactionId());
        jsonObject.put("transactionType", transaction.getTransactionType());
        nettyClient.send(jsonObject);

        System.out.println("注册分支事务");
    }

    /**
     * 提交全局分布式事务
     *
     * @param groupId
     */
    public static void commitGlobalTransaction(String groupId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("groupId", groupId);
        jsonObject.put("command", CommandType.commit.name());
        nettyClient.send(jsonObject);

        System.out.println("提交全局事务");
    }

    public static MyTransaction getMyTransaction(String groupId) {
        return transactionMap.get(groupId);
    }

    public static MyTransaction getCurrent() {
        return current.get();
    }

    public static String getCurrentGroupId() {
        return currentGroupId.get();
    }

    public static void setCurrentGroupId(String groupId) {
        currentGroupId.set(groupId);
    }
}

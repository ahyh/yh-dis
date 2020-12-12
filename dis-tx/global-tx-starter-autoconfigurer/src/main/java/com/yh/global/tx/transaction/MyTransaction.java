package com.yh.global.tx.transaction;


import com.yh.global.tx.enums.TransactionType;
import com.yh.global.tx.util.Task;

/**
 * 自定义事务
 *
 * @author yanhuan
 */
public class MyTransaction {

    private String transactionId;
    private TransactionType transactionType;
    private String groupId;
    private Task task;

    public MyTransaction(String transactionId, String groupId) {
        this.transactionId = transactionId;
        this.groupId = groupId;
        task = new Task();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}

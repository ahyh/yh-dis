package com.yh.global.tx.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yh.global.tx.enums.TransactionType;
import com.yh.global.tx.manager.GlobalTransactionManager;
import com.yh.global.tx.transaction.MyTransaction;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Netty客户端
 *
 * @author yanhuan
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private ChannelHandlerContext context;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("接收数据：" + msg.toString());

        JSONObject jsonObject = JSON.parseObject((String) msg);
        // create-开启事务，registry-注册分支事务，commit-提交事务
        String command = jsonObject.getString("command");
        // 事务组id
        String groupId = jsonObject.getString("groupId");

        System.out.println("接收command：" + command);

        MyTransaction transaction = GlobalTransactionManager.getMyTransaction(groupId);
        if (TransactionType.commit.name().equals(command)) {
            transaction.setTransactionType(TransactionType.commit);
        } else if (TransactionType.rollback.name().equals(command)) {
            transaction.setTransactionType(TransactionType.rollback);
        }
        transaction.getTask().signalTask();
    }

    public void call(JSONObject data) {
        context.writeAndFlush(data.toJSONString()).channel().newPromise();
    }
}

package com.yh.tc;

public class Main {

    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer();
        nettyServer.start("localhost", 9090);
        System.out.println("netty server start success");
    }
}

package org.debug.smartdeviceiot.server.gateway;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理连接工具类
 * */

public class ConnectionUtil {

    public static final ConcurrentHashMap<String,SConnection> connections=
            new ConcurrentHashMap<>();

    /**
     * 绑定设备id和channel,可通过设备id找到对应的channel,并进行数据下发
     * */
    public void addConnection(SConnection connection){
        connections.put(connection.getDeviceId(), connection);
        connection.getChannel().attr(Const.connection).set(connection);
    }

    //查找连接
    public SConnection getConnection(String deviceId){
        return connections.get(deviceId);
    }

    //断开连接
    public void logout(SConnection connection){
        connections.remove(connection.getDeviceId());
        SConnection curConn= connection.getChannel().attr(Const.connection).get();
        curConn.getChannel().close();
    }

    //数据下发
    public void write(byte[] bytes,SConnection connection){
        connection.getChannel().writeAndFlush(bytes);
    }

}

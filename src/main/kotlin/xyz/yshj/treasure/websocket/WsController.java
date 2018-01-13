package xyz.yshj.treasure.websocket;


import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * websocket 控制器
 *
 * @ServerEndpoint 标明了地址 和@RequestMapping类似
 */
@ServerEndpoint(value = "/websocket")
@Component
public class WsController {

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        WsUtils.getWebSocketSet().add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());

        WsUtils.sendInfo("当前有" + getOnlineCount() + "人在线");

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        WsUtils.getWebSocketSet().remove(this);  //从set中删除
        subOnlineCount();           //在线数减1

        WsUtils.sendInfo("有一连接关闭！当前在线人数为" + getOnlineCount());
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端 " + session.getId() + " 的消息:" + message);

        //群发消息
        WsUtils.sendInfo("来自 " + session.getId() + " 的消息:" + message);

    }

    /**
     * 发生错误时调用
     *
     * @OnError
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误" + session.getId());
        error.printStackTrace();
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    private static synchronized int getOnlineCount() {
        return WsUtils.getOnlineCount();
    }

    private static synchronized void addOnlineCount() {
        WsUtils.setOnlineCount(WsUtils.getOnlineCount() + 1);
    }

    private static synchronized void subOnlineCount() {
        WsUtils.setOnlineCount(WsUtils.getOnlineCount() - 1);
    }
}
package xyz.yshj.treasure.websocket


import org.json.JSONObject
import org.springframework.stereotype.Component

import javax.websocket.*
import javax.websocket.server.ServerEndpoint
import java.io.IOException
import java.util.concurrent.CopyOnWriteArraySet

/**
 * websocket 控制器
 *
 * @ServerEndpoint 标明了地址 和@RequestMapping类似
 */
@ServerEndpoint(value = "/websocket")
@Component
class WsController {

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private var session: Session? = null

    private val onlineCount: Int
        @Synchronized get() = WsUtils.config["onlineCount"] ?: 0

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    fun onOpen(session: Session) {
        this.session = session
        WsUtils.webSocketSet.add(this)     //加入set中
        addOnlineCount()           //在线数加1
        println("有新连接加入！当前在线人数为" + onlineCount)

        WsUtils.sendInfo("当前有" + onlineCount + "人在线")

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    fun onClose() {
        WsUtils.webSocketSet.remove(this)  //从set中删除
        subOnlineCount()           //在线数减1

        WsUtils.sendInfo("有一连接关闭！当前在线人数为" + onlineCount)
        println("有一连接关闭！当前在线人数为" + onlineCount)
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    fun onMessage(message: String, session: Session) {
        //        var msg = {
        //                "level_min": oLevelMin.value,
        //                "level_max": oLevelMax.value,
        //                "price_min": oPriceMin.value,
        //                "price_max": oPriceMax.value
        //        }
        try {
            val params = JSONObject(message)
            if (!params.isNull("level_min")) {
                val levelMin = params.getString("level_min").toIntOrNull()
                if (levelMin != null) {
                    WsUtils.config["level_min"] = levelMin
                }
            }
            if (!params.isNull("level_max")) {
                val levelMax = params.getString("level_max").toIntOrNull()
                if (levelMax != null) {
                    WsUtils.config["level_max"] = levelMax
                }
            }
            if (!params.isNull("price_min")) {
                val priceMin = params.getString("price_min").toIntOrNull()
                if (priceMin != null) {
                    WsUtils.config["price_min"] = priceMin * 100
                }
            }
            if (!params.isNull("price_max")) {
                val priceMax = params.getString("price_max").toIntOrNull()
                if (priceMax != null) {
                    WsUtils.config["price_max"] = priceMax * 100
                }
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }



        println("来自客户端 " + session.id + " 的消息:" + message)

        //群发消息
        WsUtils.sendInfo("来自 " + session.id + " 的消息:" + message)

    }

    /**
     * 发生错误时调用
     *
     * @OnError
     */
    @OnError
    fun onError(session: Session, error: Throwable) {
        println("发生错误" + session.id)
        error.printStackTrace()
    }


    @Throws(IOException::class)
    fun sendMessage(message: String) {
        this.session!!.basicRemote.sendText(message)
        //this.session.getAsyncRemote().sendText(message);
    }

    @Synchronized
    private fun addOnlineCount() {
        WsUtils.config["onlineCount"] = (WsUtils.config["onlineCount"] ?: 0) + 1
    }

    @Synchronized
    private fun subOnlineCount() {
        WsUtils.config["onlineCount"] = (WsUtils.config["onlineCount"] ?: 0) - 1
    }
}
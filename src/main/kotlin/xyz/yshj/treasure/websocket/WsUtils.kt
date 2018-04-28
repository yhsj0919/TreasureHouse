package xyz.yshj.treasure.websocket

import java.io.IOException
import java.util.concurrent.CopyOnWriteArraySet


object WsUtils {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    @JvmStatic
    var onlineCount = 0

    @JvmStatic
    var level_min = 69

    @JvmStatic
    var level_max = 69

    @JvmStatic
    var price_min = 86000
    @JvmStatic
    var price_max: Int? = null

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    @JvmStatic
    var webSocketSet = CopyOnWriteArraySet<WsController>()

    /**
     * 群发自定义消息
     */
    @JvmStatic
    fun sendInfo(message: String) {
        for (item in WsUtils.webSocketSet) {
            try {
                item.sendMessage(message)
            } catch (e: IOException) {
                continue
            }
        }
    }

}

package xyz.yshj.treasure.websocket

import java.io.IOException
import java.util.concurrent.CopyOnWriteArraySet


object WsUtils {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    @JvmStatic
    val config = hashMapOf<String, Int?>("onlineCount" to 0, "level_min" to 69, "level_max" to 69, "price_min" to 86000, "price_max" to 100000000)

//
//    @JvmStatic
//    var onlineCount = 0
//
//    var level_min = 69
//
//    var level_max = 69
//
//    var price_min = 86000
//
//    var price_max: Int? = null

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    @JvmStatic
    val webSocketSet = CopyOnWriteArraySet<WsController>()

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

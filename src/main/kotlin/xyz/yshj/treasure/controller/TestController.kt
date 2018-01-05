package xyz.yshj.treasure.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import xyz.yshj.treasure.websocket.WsUtils
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 控制器
 * Created by LOVE on 2016/12/28 028.
 */
@RestController
@RequestMapping(value = ["/api"], produces = ["application/json;charset=UTF-8"])
class TodoController {

    /**
     * 查询TODO列表
     */
    @RequestMapping(method = [RequestMethod.GET])
    fun getAuthorList(request: HttpServletRequest): Map<String, Any> {

        WsUtils.webSocketSet.forEach {

            it.sendMessage("测试")
        }

        return mapOf("name" to "测试")
    }

    /**
     * 查询TODO列表
     */
    @RequestMapping(value = ["/test"], method = [RequestMethod.GET])
    fun findTodo(request: HttpServletRequest): Map<String, Any> {
        return mapOf("name" to "test")
    }


}

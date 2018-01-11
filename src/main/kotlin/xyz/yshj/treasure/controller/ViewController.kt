package xyz.yshj.treasure.controller

import org.springframework.stereotype.Controller
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
@Controller
@RequestMapping(value = ["/view"], produces = ["application/json;charset=UTF-8"])
class ViewController {

    /**
     * 消息接收页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = ["/treasure"], method = [RequestMethod.GET])
    fun treasure(request: HttpServletRequest, response: HttpServletResponse): ModelAndView {

        return ModelAndView("client")
    }

    @RequestMapping(value = ["/test"], method = [RequestMethod.GET])
    fun test(request: HttpServletRequest, response: HttpServletResponse): ModelAndView {

        return ModelAndView("ddd")
    }


}

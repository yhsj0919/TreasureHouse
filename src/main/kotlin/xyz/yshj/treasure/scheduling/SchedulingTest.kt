package xyz.yshj.treasure.scheduling

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import xyz.yshj.treasure.bean.RespData
import xyz.yshj.treasure.bean.UserInfoBean
import xyz.yshj.treasure.utils.*
import xyz.yshj.treasure.websocket.WsUtils
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * 定时任务
 * Created by LOVE on 2017/1/3 003.
 */
@Component
open class SchedulingTest {

    private var oldInfo = hashSetOf<String?>()


    //    一个cron表达式有至少6个（也可能7个）有空格分隔的时间元素。
    //    按顺序依次为
    //    秒（0~59）
    //    分钟（0~59）
    //    小时（0~23）
    //    天（月）（0~31，但是你需要考虑你月的天数）
    //    月（0~11）
    //    天（星期）（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT）
    //    7.年份（1970－2099）


    //其中每个元素可以是一个值(如6),一个连续区间(9-12),一个间隔时间(8-18/4)(/表示每隔4小时),一个列表(1,3,5),通配符。由于"月份中的日期"和"星期中的日期"这两个元素互斥的,必须要对其中一个设置?.

    //0 0 10,14,16 * * ? 每天上午10点，下午2点，4点
    //0 0/30 9-17 * * ?   朝九晚五工作时间内每半小时
    //0 0 12 ? * WED 表示每个星期三中午12点
    //"0 0 12 * * ?" 每天中午12点触发
    //"0 15 10 ? * *" 每天上午10:15触发
    //"0 15 10 * * ?" 每天上午10:15触发
    //"0 15 10 * * ? *" 每天上午10:15触发
    //"0 15 10 * * ? 2005" 2005年的每天上午10:15触发
    //"0 * 14 * * ?" 在每天下午2点到下午2:59期间的每1分钟触发
    //"0 0/5 14 * * ?" 在每天下午2点到下午2:55期间的每5分钟触发
    //"0 0/5 14,18 * * ?" 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发
    //"0 0-5 14 * * ?" 在每天下午2点到下午2:05期间的每1分钟触发
    //"0 10,44 14 ? 3 WED" 每年三月的星期三的下午2:10和2:44触发
    //"0 15 10 ? * MON-FRI" 周一至周五的上午10:15触发
    //"0 15 10 15 * ?" 每月15日上午10:15触发
    //"0 15 10 L * ?" 每月最后一日的上午10:15触发
    //"0 15 10 ? * 6L" 每月的最后一个星期五上午10:15触发
    //"0 15 10 ? * 6L 2002-2005" 2002年至2005年的每月的最后一个星期五上午10:15触发
    //"0 15 10 ? * 6#3" 每月的第三个星期五上午10:15触发


    @Scheduled(cron = "0/2 * * * * ?") // 每天16点
    fun scheduler() {

        if (WsUtils.config["onlineCount"] == 0) {
            oldInfo.clear()
            return
        }


        val price_min = if (WsUtils.config["price_min"] != null) {
            "&price_min=" + WsUtils.config["price_min"]
        } else {
            ""
        }

        val price_max = if (WsUtils.config["price_max"] != null) {
            "&price_max=" + WsUtils.config["price_max"]
        } else {
            ""
        }

        "http://recommd.xyq.cbg.163.com/cgi-bin/recommend.py?_=${Date().time}&level_min=${WsUtils.config["level_min"]}&level_max=${WsUtils.config["level_max"]}&server_type=3&price_min=$price_min$price_max&act=recommd_by_role&page=1&count=20&search_type=overall_search_role"
                .get<RespData>()
                .subscribe({ resp ->

                    val tmp = ArrayList<UserInfoBean>()

                    resp
                            .equips
                            ?.reversed()
                            ?.forEach {
                                //是否首次上架
                                val sellingTime = it.selling_time.time(def = 100) / 1000
                                val createTime = it.create_time.time() / 1000

                                if (Math.abs(createTime - sellingTime) <= 60) {
                                    if (it.eid !in oldInfo) {
                                        tmp.add(it)
                                    }
                                }
                            }

                    if (tmp.size > 0) {
                        WsUtils.sendInfo(tmp.json())
                        println("有新消息推送")
                    } else {
                        println("不需要推送")
                    }

                    resp.equips?.forEach {
                        oldInfo.add(it.eid)
                    }

                    if (oldInfo.size < 20) {
                        print(oldInfo.json())
                    }


                }, {
                    it.printStackTrace()
                })
    }
}

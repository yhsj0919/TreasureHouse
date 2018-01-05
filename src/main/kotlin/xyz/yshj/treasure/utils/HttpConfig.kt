package xyz.yshj.treasure.utils

import khttp.structures.cookie.CookieJar

object HttpConfig {
    var K_COOKIE = CookieJar() //cookie
    val K_HEADER = mapOf(
            "Accept" to "application/json",
            "Accept-Encoding" to "gzip, deflate, sdch",
            "Accept-Language" to "zh-CN,zh;q=0.8",
            "Connection" to "keep-alive",
            "Host" to "xyq.cbg.163.com",
            "User-Agent" to "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 Edge/16.16299",
            "X-Request" to "JSON",
            "X-Requested-With" to "XMLHttpRequest"
    )

//    var K_COOKIE = mapOf(
//            "_ntes_nnid" to "de920d23f336f7fa44fa3c5fba55d5c9,1514950139841",
//            "_ntes_nuid" to "de920d23f336f7fa44fa3c5fba55d5c9",
//            "overall_sid" to "V9gQJDNYLYUHgHiiSkXEI-hjvsZU1mY0AzEWdqHF",
//            "__session__" to "1",
//            "no_login_mark" to "1",
//            "no_login_mark" to "1",
//            "fingerprint" to "2192328484"
//    )
}
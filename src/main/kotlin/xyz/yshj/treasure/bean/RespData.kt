package xyz.yshj.treasure.bean

data class RespData(
        var status: Int = 0,
        var equips: ArrayList<UserInfoBean>? = ArrayList(),
        var pager: PagerBean? = null
)



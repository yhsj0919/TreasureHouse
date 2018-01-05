package xyz.yshj.treasure.bean

data class UserInfoBean(
        var area_name: String? = null,
        var server_name: String? = null,
        var time_left: String? = null,
        var eid: String? = null,
        var server_id: String? = null,
        //门派
        var school: Int? = null,
        //收藏
        var collect_num: Int? = null,
        //价格
        var price: String? = null

)

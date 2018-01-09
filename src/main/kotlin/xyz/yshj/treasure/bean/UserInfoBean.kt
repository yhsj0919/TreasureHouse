package xyz.yshj.treasure.bean

data class UserInfoBean(
        //等级描述
        var equip_level_desc: String? = null,
        //精炼水平
        var jinglian_level: Int = 0,
        //int类型的价格,乘了100
        var price_int: Long = 0,
        //气血
        var qixue: Int = 0,
        //最大法术
        var max_expt_fashu: Int = 0,
        //法力
        var addon_fali: Int = 0,
        //命中
        var mingzhong: Int = 0,
        //状态
        var addon_status: Int = 0,
        //剩余时间
        var time_left: String? = null,
        //价格
        var price: String? = null,
        //是否收藏
        var is_collect: Int = 0,
        //储存类型
        var storage_type: Int = 0,
        //创建时间
        var create_time: String? = null,
        //装备ID?
        var equipid: Long = 0,
        //服务器序列号
        var server_sn: String? = null,
        //封印
        var anti_fengyin: Int = 0,
        //灵力
        var addon_lingli: Int = 0,
        //法术
        var expt_fashu: Int = 0,
        //收藏数
        var collect_num: Int = 0,
        //最大防御
        var max_expt_fangyu: Int = 0,
        //
        var appointed_roleid: String? = null,
        //服务器Id
        var server_id: Int = 0,
        //体质
        var addon_tizhi: Int = 0,
        //敏捷
        var addon_minjie: Int = 0,
        //服务器名称
        var server_name: String? = null,
        //防御
        var expt_fangyu: Int = 0,
        //亮点
        var highlights: List<List<String>>? = null,
        //
        var addon_effect_chance: Int = 0,
        //灵力
        var lingli: Int = 0,
        //宝宝防御
        var bb_expt_fangyu: Int = 0,
        //损坏?
        var damage: Int = 0,
        //套装效果
        var suit_effect: Int = 0,
        //宝宝攻击
        var bb_expt_gongji: Int = 0,
        //分数
        var score: Int = 0,
        //宝宝法术
        var bb_expt_fashu: Int = 0,
        //宝石等级
        var gem_level: Int = 0,
        //宝宝法抗
        var bb_expt_kangfa: Int = 0,
        //装备等级
        var equip_level: Int = 0,
        //力量
        var addon_liliang: Int = 0,
        //
        var min_buyer_level: Int = 0,
        //区域名称
        var area_name: String? = null,
        //速度
        var speed: Int = 0,
        //商品ID
        var eid: String? = null,
        //状态
        var status: Int = 0,
        //耐力
        var addon_naili: Int = 0,
        //装备类星星
        var equip_type: String? = null,
        //法力
        var hp: Int = 0,
        //法抗
        var expt_kangfa: Int = 0,
        //装备类型描述
        var equip_type_desc: String? = null,
        //魔法伤害
        var magic_damage: Int = 0,
        //伤害
        var shanghai: Int = 0,
        //敏捷
        var minjie: Int = 0,
        //最大攻击
        var max_expt_gongji: Int = 0,
        //标记
        var tag: String? = null,
        //
        var addon_skill_chance: Int = 0,
        //镶嵌等级
        var xiang_qian_level: Int = 0,
        //服务器ID
        var serverid: Int = 0,
        //套装技能
        var suit_skill: Int = 0,
        //攻击
        var expt_gongji: Int = 0,
        //图标
        var icon: String? = null,
        //
        var kindid: Int = 0,
        //门派
        var school: Int = 0,
        //
        var zongshang: Int = 0,
        //等级
        var level: Int = 0,
        //特效
        var special_effect: Int = 0,
        //游戏订单编号
        var game_ordersn: String? = null,
        //装备图片
        var equip_face_img: String? = null,
        //防御
        var fangyu: Int = 0,
        //到期时间
        var expire_time: String? = null,
        //封印
        var fengyin: Int = 0,
        //防御
        var defense: Int = 0,
        //装备名称
        var equip_name: String? = null,
        //魔法防御
        var magic_defense: Int = 0,
        //特长
        var special_skill: Int = 0,
        //最大法抗
        var max_expt_kangfa: Int = 0,
        //
        var gem_value: Int = 0,
        //出售时间
        var selling_time: String? = null,
        //总数
        var addon_total: Int = 0,
        //卖方..
        var seller_roleid: String? = null,
        //魔法
        var mofa: Int = 0

)

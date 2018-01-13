var oConnect = document.getElementById('connect');
var oSend = document.getElementById('send');
var oClose = document.getElementById('close');
var oInput = document.getElementById('message');
var oInfo = document.getElementById('info');
var oResult = document.getElementById('result');
var chatAudio = document.getElementById('chatAudio');

var ws = null;

var tabHeard =
    '<tr >' +
    '<th width="54">角色</th>' +
    '<th>门派</th>' +
    '<th>等级</th>' +
    '<th>修炼</th>' +
    '<th>宝宝</th>' +
    '<th>亮点</th>' +
    '<th>价格</th>' +
    '<th>服务器</th>' +
    '<th>收藏</th>' +
    '<th>首次上架</th>' +
    '<th>出售剩余时间</th>' +
    '</tr>'

oConnect.onclick = function () {
    if (ws) {
        alert("服务已连接")
        return
    }

    ws = new WebSocket('ws://' + document.location.host + '/websocket');
    ws.onopen = function () {
        oInfo.innerText = "客户端已连接"
    }
    ws.onmessage = function (evt) {

        if (!isJSON(evt.data)) {
            oInfo.innerText = evt.data
            return
        }

        var info = tabHeard

        var datas = eval(evt.data)
        for (i in datas) {

            //价格
            var priceHtml = ""

            var tmpPrice = parseFloat(datas[i].price)
            if (tmpPrice > 99999) {
                priceHtml = '<span class="p1000000">' + datas[i].price + "</span>"
            } else if (tmpPrice > 9999) {
                priceHtml = '<span class="p100000">' + datas[i].price + "</span>"
            } else if (tmpPrice > 999) {
                priceHtml = '<span class="p10000">' + datas[i].price + "</span>"
            } else if (tmpPrice > 0) {
                priceHtml = '<span class="p1000">' + datas[i].price + "</span>"
            }
            //详情地址
            var url = "http://xyq.cbg.163.com/equip?s=" + datas[i].server_id + "&amp;eid=" + datas[i].eid + "&amp;o&amp;equip_refer=1"

            //亮点
            var highlights = datas[i].highlights
            var highHtml = ""
            for (index in highlights) {
                highHtml += highlights[index][0] + '<br/>'
            }

            //是否首次上架
            var sellingTime = Date.parse(new Date(datas[i].selling_time)) / 1000;
            var createTime = Date.parse(new Date(datas[i].create_time)) / 1000;

            var fistSell = ""
            if ((Math.abs(createTime - sellingTime) <= 10)) {
                var fistSell = "<span class='p100000'>是</span>"
                info += '<tr class="firstSell" onclick="window.open(\'' + url + '\');">'
            } else {
                info += '<tr class="oldSell" onclick="window.open(\'' + url + '\');">'
            }

            //判断是否提醒
            if (i == 0) {
                if ((Math.abs(createTime - sellingTime) <= 10)) {
                    chatAudio.play()
                }
            }

            //获取头像ID
            var iconId = 1
            if (datas[i].icon > 12 && datas[i].icon < 25) {
                iconId = datas[i].icon - 12
            } else if (datas[i].icon > 211 && datas[i].icon < 223) {
                iconId = datas[i].icon - 12
            } else {
                iconId = datas[i].icon
            }

            var icon = "http://res.xyq.cbg.163.com/images/role_icon/small/" + iconId + ".gif"


            //消息拼装
            info +=
                '<td>' +
                '    <img src="' + icon + '" width="50" height="50"/>' +
                '</td>' +
                '<td>' + getSchool(datas[i].school) + '</td>' +
                '<td>' + datas[i].level + '</td>' +
                '<td>' + datas[i].expt_gongji + "&nbsp;" + datas[i].expt_fangyu + "&nbsp;" + datas[i].expt_fashu + "&nbsp;" + datas[i].expt_kangfa +
                '</td>' +
                '<td>' + datas[i].bb_expt_gongji + "&nbsp;&nbsp;" + datas[i].bb_expt_fangyu + "&nbsp;&nbsp;" + datas[i].bb_expt_fashu + "&nbsp;&nbsp;" + datas[i].bb_expt_kangfa +
                '</td>' +
                '<td><span class="p1000">' + highHtml +
                '</span></td>' +
                '<td>' + priceHtml +
                '</td>' +
                '<td>' + datas[i].area_name + "-" + datas[i].server_name + '</td>' +
                '<td>' + datas[i].collect_num + '</td>' +
                '<td>' + fistSell + '</td>' +
                '<td>' + datas[i].time_left + '</td>' +
                '</tr>'

        }

        oResult.innerHTML = info
    }
    ws.onclose = function () {
        oInfo.innerText = "客户端已断开连接"
        ws = null
    };
    ws.onerror = function (evt) {
        oInfo.innerText = "连接出错" + evt.data
        ws = null
    };
};
oSend.onclick = function () {
    if (ws) {
        ws.send(oInput.value);
    } else {
        alert("请先连接服务")
    }
}
oClose.onclick = function () {
    if (ws) {
        ws.close()
    }
}


function getSchool(school) {
    var schoolName = {
        1: "大唐官府",
        2: "化生寺",
        3: "女儿村",
        4: "方寸山",
        5: "天宫",
        6: "普陀山",
        7: "龙宫",
        8: "五庄观",
        9: "狮驼岭",
        10: "魔王寨",
        11: "阴曹地府",
        12: "盘丝洞",
        13: "神木林",
        14: "凌波城",
        15: "无底洞"
    }
    return schoolName[school]
}

/**
 * 判断是不是json字符串
 * @param str
 * @returns {boolean}
 */
function isJSON(str) {
    if (typeof str == 'string') {
        try {
            var obj = JSON.parse(str);
            if (str.indexOf('{') > -1) {
                return true;
            } else {
                return false;
            }

        } catch (e) {
            return false;
        }
    }
    return false;
}
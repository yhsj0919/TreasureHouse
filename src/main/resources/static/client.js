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
    '<th>首次上架</th>' +
    '<th>出售剩余时间</th>' +
    '<th>收藏</th>' +
    '<th>详情</th>' +
    '</tr>'

oConnect.onclick = function () {
    ws = new WebSocket('ws://' + document.location.host + '/websocket');
    ws.onopen = function () {
        oInfo.innerText = "客户端已连接"
    }
    ws.onmessage = function (evt) {

        oInfo.innerText = "获取新的消息"

        var info = tabHeard

        var datas = eval(evt.data)
        for (i in datas) {

            if (i == 0) {
                if (datas[i].collect_num == 0) {
                    chatAudio.play()
                }
            }

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


            var url = "http://xyq.cbg.163.com/equip?s=" + datas[i].server_id + "&amp;eid=" + datas[i].eid + "&amp;o&amp;equip_refer=1"

            info +=
                '<tr>' +
                '<td>' +
                '    <img src="http://res.xyq.cbg.163.com/images/role_icon/small/2.gif" width="50" height="50"/>' +
                '</td>' +
                '<td>' + getSchool(datas[i].school) + '</td>' +
                '<td>' + datas[i].level + '</td>' +
                '<td>' + datas[i].expt_gongji + "&nbsp;" + datas[i].expt_fangyu + "&nbsp;" + datas[i].expt_fashu + "&nbsp;" + datas[i].expt_kangfa +
                '</td>' +
                '<td>' + datas[i].bb_expt_gongji + "&nbsp;&nbsp;" + datas[i].bb_expt_fangyu + "&nbsp;&nbsp;" + datas[i].bb_expt_fashu + "&nbsp;&nbsp;" + datas[i].bb_expt_kangfa +
                '</td>' +
                '<td></td>' +
                '<td>' + priceHtml +
                '</td>' +
                '<td>' + datas[i].area_name + "-" + datas[i].server_name + '</td>' +
                '<td>' + datas[i].create_time + '</td>' +
                '<td>' + datas[i].time_left + '</td>' +
                '<td>' + datas[i].collect_num + '</td>' +
                '<td>' + '<a href="' + url + '" target="_blank" class="linkText">去藏宝阁查看详情</a>' + '</td>' +
                '</tr>'

        }

        oResult.innerHTML = info
    }
    ws.onclose = function () {
        oInfo.innerText = "客户端已断开连接"
    };
    ws.onerror = function (evt) {
        oInfo.innerText = evt.data
    };
};
oSend.onclick = function () {
    if (ws) {
        ws.send(oInput.value);
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
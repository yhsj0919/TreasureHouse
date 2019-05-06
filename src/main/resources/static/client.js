var oConnect = document.getElementById('connect');
var oSend = document.getElementById('send');
var oClose = document.getElementById('close');
var oLevelMin = document.getElementById('level_min');
var oLevelMax = document.getElementById('level_max');
var oPriceMin = document.getElementById('price_min');
var oPriceMax = document.getElementById('price_max');
var oInfo = document.getElementById('info');
var oResult = document.getElementById('result');
var chatAudio = document.getElementById('chatAudio');

var ws = null;

var tabHeard =
    '<tr id="tab_title">' +
    '<th width="54">角色</th>' +
    '<th>门派</th>' +
    '<th>等级</th>' +
    '<th>修炼</th>' +
    '<th>宝宝</th>' +
    '<th>亮点</th>' +
    '<th>价格</th>' +
    '<th>服务器</th>' +
    '<th>收藏</th>' +
    '<th>上架时间</th>' +
    '<th>出售剩余时间</th>' +
    '</tr>';

oConnect.onclick = function () {
    if (ws) {
        alert("服务已连接");
        return
    }

    ws = new WebSocket('ws://' + document.location.host + '/websocket');
    ws.onopen = function () {
        oInfo.innerText = "客户端已连接"
    };
    ws.onmessage = function (evt) {

        if (!isJSON(evt.data)) {
            oInfo.innerText = evt.data;
            return
        }

        var datas = eval(evt.data);
        for (i in datas) {
            //价格
            var priceHtml = "";

            var tmpPrice = parseFloat(datas[i].price);
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
            var url = "http://xyq.cbg.163.com/equip?s=" + datas[i].server_id + "&eid=" + datas[i].eid + "&o&equip_refer=1";

            //亮点
            var highlights = datas[i].highlights;
            var highHtml = "";
            for (index in highlights) {
                highHtml += highlights[index][0] + '<br/>'
            }

            var fistSell = '<span class="p100000">' + datas[i].create_time + '</span>';


            //判断是否提醒
            if (i == 0) {
                chatAudio.play()
            }

            //获取头像ID
            var iconId = 1;
            if (datas[i].icon > 12 && datas[i].icon < 25) {
                iconId = datas[i].icon - 12
            } else if (datas[i].icon > 211 && datas[i].icon < 223) {
                iconId = datas[i].icon - 12
            } else {
                iconId = datas[i].icon
            }

            var icon = "https://cbg-xyq.res.netease.com/images/role_icon/small/" + iconId + ".gif";

            //消息拼装
            var info =
                '<tr class="firstSell" onclick="window.open(\'' + url + '\');">' +
                '<td xmlns="http://www.w3.org/1999/html">' +
                '    <img src="' + icon + '" width="50" height="50"/>' +
                '</td>' +
                '<td><a href="' + url + '" target="_blank"> ' + getSchool(datas[i].school) + '</a></td>' +
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
                '</tr>';

            var tr = oResult.insertRow(1);
            tr.className = "firstSell";
            tr.onclick = function (ev) {
                window.open(url)
            };

            // insertCell() 方法用于在 HTML 表的一行的指定位置插入一个空的
            // td为插入的行的第一个td元素
            tr.innerHTML = info;
        }
    };
    ws.onclose = function () {
        oInfo.innerText = "客户端已断开连接";

        oResult.innerHTML = tabHeard;
        ws = null
    };
    ws.onerror = function (evt) {
        oInfo.innerText = "连接出错" + evt.data;
        ws = null
    };
};
oSend.onclick = function () {
    if (ws) {

        var msg = {};
        msg['level_min'] = oLevelMin.value;
        msg['level_max'] = oLevelMax.value;
        msg['price_min'] = oPriceMin.value;
        msg['price_max'] = oPriceMax.value;


        ws.send(JSON.stringify(msg));
    } else {
        alert("请先连接服务")
    }
};
oClose.onclick = function () {
    if (ws) {
        ws.close()
    }
};


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
        15: "无底洞",
        16: "女魃墓",
        17: "天机城",
        18: "花果山"
    };
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
var table=document.getElementById("mission_receive");
var loc = location.href;
var n1 = loc.length;//地址的总长度
var n2 = loc.indexOf("=");//取得=号的位置
var id = decodeURI(loc.substr(n2+1, n1-n2));//从=号后面的内容
$.ajax({
    url: "/Servlet",
    type: "POST",
    data: {
        "name": id,
        "action": "getProjectInfo"
    },
    success: function (data) {
        for (var i = 0; i < data.taskIds.size(); i++) {
            var oneRow = table.insertRow();//插入一行
            var cell1 = oneRow.insertCell();//单单插入一行是不管用的，需要插入单元格
            var cell2 = oneRow.insertCell();
            var cell3 = oneRow.insertCell();
            cell1.innerHTML("");
            cell2.innerHTML("");
            cell3.innerHTML("")
        }
    },
    error: function () {
        alert("Wrong!");
    }
});


function getRoom() {

  var roomId = getRoomId();

  var param = "command=getRoom";
  httpPost("srv.php", param, function(response) {
    try {
      var json = JSON.parse(response);
      if (json.result == "0") {
        for (var i = 0; i < json.rooms[i]; i++) {
          if (json.rooms[i].id === roomId) {
            display(json.rooms[i]);
            break;
          }
        }
      } else {
        alert("物件情報の取得に失敗しました");
      }
    } catch(e) {
      alert("物件情報の取得に失敗しました");
    }
  });
}

function display(room) {
/*
  for (var i = 0; i < rooms.length; i++) {
    var table = document.getElementById("room_table");
    var tr = table.insertRow(-1);

    var tdId = tr.insertCell(-1);
    tdId.innerHTML = rooms[i].id;

    var tdStatus = tr.insertCell(-1);
    if (rooms[i].approval === "1") {
      tdStatus.innerHTML = "掲載中";
    } else {
      tdStatus.innerHTML = "審査待ち";
    }

    var tdName = tr.insertCell(-1);
    tdName.innerHTML = base64Decode(rooms[i].name);

    var tdPlace = tr.insertCell(-1);
    tdPlace.innerHTML = base64Decode(rooms[i].place);

    var tdRent = tr.insertCell(-1);
    tdRent.innerHTML = rooms[i].rent;

    var tdEdit = tr.insertCell(-1);
    var href = "javascript:onClickEdit(\"" + rooms[i].id + "\")";
    tdEdit.innerHTML = "<a href='" + href + "'><img src='img/edit.png' style='width:20px'></a>";
  }
  */
}

function getRoomId() {

  
  return "";
}

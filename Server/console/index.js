
function getRoom() {

  var param = "command=getRoom";
  httpPost("srv.php", param, function(response) {
    try {
      var json = JSON.parse(response);
      if (json.result == "0") {
        display(json.rooms);
      } else {
        alert("物件情報の取得に失敗しました");
      }
    } catch(e) {
      alert("物件情報の取得に失敗しました");
    }
  });
}

function display(rooms) {

  for (var i = 0; i < rooms.length; i++) {
    var table = document.getElementById("room_table");
    var tr = table.insertRow(-1);

    var tdId = tr.insertCell(-1);
    tdId.innerHTML = rooms[i].id;

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
}

function onClickEdit(id) {

  location.href = "edit.html?id=" + id;
}

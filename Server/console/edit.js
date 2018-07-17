
function getRoom() {

  var roomId = getParam("id");

  var param = "command=getRoom";
  httpPost("srv.php", param, function(response) {
    try {
      var json = JSON.parse(response);
      if (json.result == "0") {
        for (var i = 0; i < json.rooms.length; i++) {
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

  document.getElementById("id").value = room.id;

  if (room.approval === "1") {
    document.getElementById("approval").checked = true;
  } else {
    document.getElementById("approval").checked = false;
  }

  document.getElementById("name").value = base64Decode(room.name);
  document.getElementById("place").value = base64Decode(room.place);

  document.getElementById("score").value = room.score;
  document.getElementById("review").value = room.review;
}

function checkScore(score) {

  if ((score === "0") || (score === "1") || (score === "2") || (score === "3") || (score === "4") || (score === "5")) {
    return true;
  } else {
    return false;
  }
}

function checkReview(review) {

  if (score.length == 0) {
    return false;
  }
  for (var i = 0; i < review.length; i++) {
    var c = review.substr(i, 1);
    if ((c === "0") || (c === "1") || (c === "2") || (c === "3") || (c === "4") || (c === "5") || (c === "6") || (c === "7") || (c === "8") || (c === "9")) {
      continue;
    } else {
      return false;
    }
  }
  return true;
}

function onClickSave() {

  var roomId = getParam("id");

  var param = "command=updateRoom&";
  param += ("roomId=" + roomId + "&");

  var approval = "0";
  if (document.getElementById("approval").checked) {
    approval = "1";
  }
  param += ("approval=" + approval + "&");

  var score = document.getElementById("score").value;
  if (checkScore(score)) {
    param += ("score=" + score + "&");
  } else {
    alert("不正な評価です");
    return;
  }

  var review = document.getElementById("review").value;
  if (checkReview(review)) {
    param += ("review=" + review);
  } else {
    alert("不正なレビュー数です。");
    return;
  }
  httpPost("srv.php", param, function(response) {
    try {
      var json = JSON.parse(response);
      if (json.result === "0") {
        alert("保存しました");
        location.href = "index.html";
      } else {
        alert("保存に失敗しました");
      }
    } catch(e) {
      alert("保存に失敗しました");
    }
  });
}

<?php

require "room.php";

$command = $_POST["command"];

if (strcmp($command, "getRoom") == 0) {
	getRoom();
} else if (strcmp($command, "updateRoom") == 0) {
	updateRoom();
} else {
  echo("unknown");
}

function getRoom() {

	$data = [];
	$rooms = Room::readAll();
	foreach($rooms as $room) {
		$data[] = Array(
				"id" => $room->id,
				"approval" => $room->approval,
				"score" => $room->score,
				"review" => $room->review,
				"name" => $room->name,
				"place" => $room->place,
				"rent" => $room->rent,
				"phone" => $room->phone,
				"email" => $room->email
			);		
	}
	echo(json_encode(Array("result" => "0",
							"rooms" => $data)));
}

function updateRoom() {

	$roomid = $_POST["roomId"];
	$approval = $_POST["approval"];
	$score = $_POST["score"];
	$review = $_POST["review"];

	if (Room::update($roomId, $approval, $score, $review)) {
		echo(json_encode(Array("result" => "0")));
	} else {
		echo(json_encode(Array("result" => "1")));
	}
}

function DebugSave($str){

	date_default_timezone_set('Asia/Tokyo');

	$d = date('Y-m-d H:i:s');
	$s = $d . " " . $str . "\r\n";
	file_put_contents("debug.txt", $s, FILE_APPEND);
}

?>

<?php

require "room.php";

$command = $_POST["command"];

DebugSave($command);

if (strcmp($command, "getRoom") == 0) {
	getRoom();
} else if (strcmp($command, "postRoom") == 0) {
	postRoom();
} else if (strcmp($command, "uploadRoomImage") == 0) {
	uploadRoomImage();
} else {
  echo("unknown");
}

function getRoom() {

	$data = [];
	$rooms = Room::readAll();
	foreach($rooms as $room) {
		if (strcmp($room->approval, "1") == 0) {
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
	}
	echo(json_encode(Array("result" => "0",
							"data" => $data)));
}

function postRoom() {

	$name = $_POST["name"];
	$place = $_POST["place"];
	$rent = $_POST["rent"];
	$phone = $_POST["phone"];
	$email = $_POST["email"];

	$roomId = Room::create($name, $place, $rent, $phone, $email);
	if (!is_null($roomId)) {
		echo(json_encode(Array("result" => "0", "roomId" => $roomId)));
	} else {
		echo(json_encode(Array("result" => "1")));
	}
}

function uploadRoomImage() {

	$roomId = $_POST["roomId"];
	$file = $_FILES['image']['tmp_name'];
	$fileName = "data/image/room/" . $roomId;

	DebugSave($fileName);

	if (move_uploaded_file($file, $fileName)) {
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

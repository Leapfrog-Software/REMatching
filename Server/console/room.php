<?php

class RoomData {
  public $id;
	public $approval;
  public $score;
  public $review;
  public $name;
  public $place;
  public $rent;
  public $phone;
  public $email;

	static function initFromFileString($line) {
		$datas = explode(",", $line);
		if (count($datas) == 9) {
      $roomData = new RoomData();
      $roomData->id = $datas[0];
			$roomData->approval = $datas[1];
      $roomData->score = $datas[2];
      $roomData->review = $datas[3];
      $roomData->name = $datas[4];
      $roomData->place = $datas[5];
      $roomData->rent = $datas[6];
      $roomData->phone = $datas[7];
      $roomData->email = $datas[8];
			return $roomData;
		}
		return null;
	}

  function toFileString() {
    $str = "";
    $str .= $this->id;
    $str .= ",";
    $str .= $this->approval;
    $str .= ",";
    $str .= $this->score;
    $str .= ",";
    $str .= $this->review;
    $str .= ",";
    $str .= $this->name;
    $str .= ",";
    $str .= $this->place;
    $str .= ",";
    $str .= $this->rent;
    $str .= ",";
    $str .= $this->phone;
    $str .= ",";
    $str .= $this->email;
    $str .= "\n";
    return $str;
  }
}

class Room {

  const FILE_NAME = "../data/room.txt";

	static function readAll() {
		if (file_exists(Room::FILE_NAME)) {
			$fileData = file_get_contents(Room::FILE_NAME);
			if ($fileData !== false) {
				$dataList = [];
				$lines = explode("\n", $fileData);
				for ($i = 0; $i < count($lines); $i++) {
					$data = RoomData::initFromFileString($lines[$i]);
					if (!is_null($data)) {
						$dataList[] = $data;
					}
				}
				return $dataList;
			}
		}
		return [];
	}

  static function update($id, $approval, $score, $review) {

    $roomList = Room::readAll();
    foreach ($roomList as &$roomData) {
      if (strcmp($roomData->id, $id) == 0) {
        $roomData->approval = "1";
        $roomData->score = $score;
        $roomData->$review = $review;
      }
    }

    $str = "";
    foreach ($roomList as $roomData) {
      $str .= $roomData->toFileString();
    }
    if (file_put_contents(Room::FILE_NAME, $roomData->toFileString()) !== FALSE) {
      return true;
    } else {
      return false;
    }
  }
}

?>

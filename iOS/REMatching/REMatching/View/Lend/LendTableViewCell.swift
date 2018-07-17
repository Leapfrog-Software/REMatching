//
//  LendTableViewCell.swift
//  REMatching
//
//  Created by 藤田 祥一 on 2018/07/17.
//  Copyright © 2018年 Leapfrog-Inc. All rights reserved.
//

import UIKit

class LendTableViewCell: UITableViewCell {
    
    @IBOutlet private weak var roomImageView: UIImageView!
    @IBOutlet private weak var nameLabel: UILabel!
    @IBOutlet private weak var placeLabel: UILabel!
    @IBOutlet private weak var rentLabel: UILabel!

    override func prepareForReuse() {
        super.prepareForReuse()
        
        ImageStorage.shared.cancelRequest(imageView: self.roomImageView)
    }
    
    func configure(roomData: RoomData) {
        
        ImageStorage.shared.fetch(url: Constants.RoomImageDirectory + roomData.id, imageView: self.roomImageView)
        
        self.nameLabel.text = roomData.name
        self.placeLabel.text = roomData.place
        
        let formatter = NumberFormatter()
        formatter.numberStyle = .decimal
        formatter.groupingSeparator = ","
        formatter.groupingSize = 3
        self.rentLabel.text = formatter.string(from: NSNumber(value: roomData.rent))
    }
}

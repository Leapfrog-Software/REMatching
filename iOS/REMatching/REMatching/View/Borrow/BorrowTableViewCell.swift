//
//  BorrowTableViewCell.swift
//  REMatching
//
//  Created by Leapfrog-Software on 2018/06/23.
//  Copyright © 2018年 Leapfrog-Inc. All rights reserved.
//

import UIKit

class BorrowTableViewCell: UITableViewCell {

    @IBOutlet private weak var roomImageView: UIImageView!
    @IBOutlet private weak var nameLabel: UILabel!
    @IBOutlet private weak var reviewLabel: UILabel!
    @IBOutlet private weak var locationLabel: UILabel!
    
    func configure(roomInfo: RoomInfo) {
        
        self.roomImageView.image = UIImage(named: roomInfo.imageName)
        self.nameLabel.text = roomInfo.name
        self.reviewLabel.text = roomInfo.review
        self.locationLabel.text = roomInfo.location
    }
}

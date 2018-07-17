//
//  LendTableViewCell.swift
//  REMatching
//
//  Created by Leapfrog-Software on 2018/07/17.
//  Copyright © 2018年 Leapfrog-Inc. All rights reserved.
//

import UIKit

class LendTableViewCell: UITableViewCell {
    
    @IBOutlet private weak var roomImageView: UIImageView!
    @IBOutlet private weak var nameLabel: UILabel!
    @IBOutlet private weak var placeLabel: UILabel!
    @IBOutlet private weak var rentLabel: UILabel!
    @IBOutlet private weak var approvalLabel: UILabel!

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
        let rent = formatter.string(from: NSNumber(value: roomData.rent)) ?? ""
        self.rentLabel.text = "賃料: " + rent + " 円/月"
        
        if roomData.approval {
            self.approvalLabel.text = "掲載中"
            self.approvalLabel.textColor = .approved
        } else {
            self.approvalLabel.text = "審査待ち"
            self.approvalLabel.textColor = .notApproved
        }
    }
}

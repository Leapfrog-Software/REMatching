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
    
    override func prepareForReuse() {
        super.prepareForReuse()
        
        ImageStorage.shared.cancelRequest(imageView: self.roomImageView)
    }
    
    func configure(roomData: RoomData) {
        
        ImageStorage.shared.fetch(url: Constants.RoomImageDirectory + roomData.id, imageView: self.roomImageView)
        self.nameLabel.text = roomData.name
        
        var review = ""
        review += (roomData.score <= 0) ? "☆" : "★"
        review += (roomData.score <= 1) ? "☆" : "★"
        review += (roomData.score <= 2) ? "☆" : "★"
        review += (roomData.score <= 3) ? "☆" : "★"
        review += (roomData.score <= 4) ? "☆" : "★"
        self.reviewLabel.text = review + " \(roomData.review)件のレビュー"
        
        self.locationLabel.text = roomData.place
    }
}

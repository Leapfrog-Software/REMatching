//
//  SearchTableViewCell.swift
//  REMatching
//
//  Created by Leapfrog-Software on 2018/07/16.
//  Copyright © 2018年 Leapfrog-Inc. All rights reserved.
//

import UIKit

class SearchTableViewCell: UITableViewCell {

    @IBOutlet private weak var nameLabel: UILabel!
    @IBOutlet private weak var selectedImageView: UIImageView!
    
    func configure(name: String, selected: Bool) {
        self.nameLabel.text = name
        self.selectedImageView.isHidden = !selected
    }
}

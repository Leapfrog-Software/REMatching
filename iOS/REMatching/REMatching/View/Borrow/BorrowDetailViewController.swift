//
//  BorrowDetailViewController.swift
//  REMatching
//
//  Created by Leapfrog-Software on 2018/06/23.
//  Copyright © 2018年 Leapfrog-Inc. All rights reserved.
//

import UIKit

class BorrowDetailViewController: UIViewController {

    @IBOutlet private weak var roomImageView: UIImageView!
    @IBOutlet private weak var nameLabel: UILabel!
    @IBOutlet private weak var reviewLabel: UILabel!
    @IBOutlet private weak var locationLabel: UILabel!
    @IBOutlet private weak var priceLabel: UILabel!
    
    private var roomData: RoomData?
    
    func set(roomData: RoomData) {
        self.roomData = roomData
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        /*
        self.roomImageView.image = UIImage(named: self.roomInfo?.imageName ?? "")
        self.nameLabel.text = self.roomInfo?.name
        self.reviewLabel.text = self.roomInfo?.review
        self.locationLabel.text = self.roomInfo?.location
        self.priceLabel.text = "128,000円"
 */
    }
    
    private func showAlert() {
        let alert = UIAlertController(title: nil, message: "本番アプリでは管理人へ連絡します。", preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        self.present(alert, animated: true)
    }
    
    @IBAction func onTapBack(_ sender: Any) {
        self.pop(animationType: .horizontal)
    }

    @IBAction func onTapCall(_ sender: Any) {
        self.showAlert()
    }
    
    @IBAction func onTapMail(_ sender: Any) {
        self.showAlert()
    }
}

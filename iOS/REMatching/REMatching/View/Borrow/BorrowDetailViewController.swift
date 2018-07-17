//
//  BorrowDetailViewController.swift
//  REMatching
//
//  Created by Leapfrog-Software on 2018/06/23.
//  Copyright © 2018年 Leapfrog-Inc. All rights reserved.
//

import UIKit
import MessageUI

class BorrowDetailViewController: UIViewController {

    @IBOutlet private weak var roomImageView: UIImageView!
    @IBOutlet private weak var nameLabel: UILabel!
    @IBOutlet private weak var reviewLabel: UILabel!
    @IBOutlet private weak var locationLabel: UILabel!
    @IBOutlet private weak var priceLabel: UILabel!
    @IBOutlet private weak var phoneButton: UIButton!
    @IBOutlet private weak var mailButton: UIButton!
    
    private var roomData: RoomData?
    
    func set(roomData: RoomData) {
        self.roomData = roomData
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        guard let roomData = self.roomData else {
            return
        }
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
        
        let formatter = NumberFormatter()
        formatter.numberStyle = .decimal
        formatter.groupingSeparator = ","
        formatter.groupingSize = 3
        let rent = formatter.string(from: NSNumber(value: roomData.rent)) ?? ""
        self.priceLabel.text = rent + "円"
        
        if roomData.phone.count == 0 {
            self.phoneButton.setTitleColor(.inactiveColor, for: .normal)
            self.phoneButton.layer.borderUIColor = .inactiveColor
        }
        if roomData.email.count == 0 {
            self.mailButton.setTitleColor(.inactiveColor, for: .normal)
            self.mailButton.layer.borderUIColor = .inactiveColor
        }
    }
    
    private func call(to phone: String) {
        
        let urlString = "tel://" + phone
        if let url = URL(string: urlString) {
            if UIApplication.shared.canOpenURL(url) {
                UIApplication.shared.open(url, options: [:], completionHandler: nil)
            }
        }
    }
    
    @IBAction func onTapBack(_ sender: Any) {
        self.pop(animationType: .horizontal)
    }

    @IBAction func onTapCall(_ sender: Any) {

        guard let phone = self.roomData?.phone else {
            return
        }
        if phone.count == 0 {
            return
        }
        let title = phone + "に発信します"
        let alert = UIAlertController(title: title, message: "よろしいですか？", preferredStyle: .alert)
        let okAction = UIAlertAction(title: "OK", style: .default, handler: { [weak self] _ in
            self?.call(to: phone)
        })
        alert.addAction(okAction)
        let cancelAction = UIAlertAction(title: "キャンセル", style: .cancel, handler: nil)
        alert.addAction(cancelAction)
        self.present(alert, animated: true, completion: nil)
    }
    
    @IBAction func onTapMail(_ sender: Any) {
        
        guard let roomData = self.roomData else {
            return
        }
        if roomData.email.count == 0 {
            return
        }
        
        if MFMailComposeViewController.canSendMail() {
            let compose = MFMailComposeViewController()
            compose.setToRecipients([roomData.email])
            compose.setSubject("お問い合わせ")
            compose.setMessageBody("お問い合わせ内容を入力してください", isHTML: false)
            self.present(compose, animated: true, completion: nil)
        }
    }
}

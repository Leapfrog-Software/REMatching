//
//  BorrowViewController.swift
//  REMatching
//
//  Created by Leapfrog-Software on 2018/06/23.
//  Copyright © 2018年 Leapfrog-Inc. All rights reserved.
//

import UIKit

struct RoomInfo {
    let imageName: String
    let name: String
    let review: String
    let location: String
}

class BorrowViewController: UIViewController {
    
    static let roomBase = [
        RoomInfo(imageName: "room0", name: "シティタワー千代田区最上階", review: "★★★★★ 12件のレビュー", location: "東京都千代田区1-1-1"),
        RoomInfo(imageName: "room1", name: "プラウドタワー麹町", review: "★★★☆☆ 1件のレビュー", location: "東京都千代田区1-1-1"),
        RoomInfo(imageName: "room2", name: "ザ・スカイタワー 21階 角部屋", review: "★★★☆☆ 2件のレビュー", location: "東京都千代田区1-1-1"),
        RoomInfo(imageName: "room3", name: "エクセルグランデ御茶ノ水", review: "★★★☆☆ 5件のレビュー", location: "東京都千代田区1-1-1"),
        RoomInfo(imageName: "room4", name: "アトレレジデンス渋谷", review: "★☆☆☆☆ 8件のレビュー", location: "東京都渋谷区1-1-1"),
        RoomInfo(imageName: "room5", name: "パークコード青山", review: "★★☆☆☆ 15件のレビュー", location: "東京都港区1-1-1"),
        RoomInfo(imageName: "room6", name: "ジオグランデ六本木", review: "☆☆☆☆☆ 0件のレビュー", location: "東京都港区1-1-1"),
        RoomInfo(imageName: "room7", name: "ブランズ西麻布", review: "☆☆☆☆☆ 0件のレビュー", location: "東京都港区1-1-1")
    ]
    
    @IBOutlet private weak var tableView: UITableView!
    
    private var rooms = [RoomInfo]()
    private var searchIndex = 0

    override func viewDidLoad() {
        super.viewDidLoad()

        self.tableView.rowHeight = UITableViewAutomaticDimension
        self.tableView.estimatedRowHeight = 280
        
        self.rooms = BorrowViewController.roomBase
    }
    
    @IBAction func onTapSearch(_ sender: Any) {
        let search = self.viewController(identifier: "SearchViewController") as! SearchViewController
        search.set(defaultIndex: self.searchIndex, completion: { index in
            self.searchIndex = index
            
            if index == 0 {
                self.rooms = BorrowViewController.roomBase
            } else if index == 1 {
                self.rooms = [
                    BorrowViewController.roomBase[0],
                    BorrowViewController.roomBase[1],
                    BorrowViewController.roomBase[2],
                    BorrowViewController.roomBase[3]
                ]
            }else if index == 2 {
                self.rooms = [BorrowViewController.roomBase[4]]
            } else if index == 3 {
                self.rooms = [
                    BorrowViewController.roomBase[5],
                    BorrowViewController.roomBase[6],
                    BorrowViewController.roomBase[7]
                ]
            }
            
            self.tableView.reloadData()
        })
        self.tabbarViewController()?.stack(viewController: search, animationType: .vertical)
    }
}

extension BorrowViewController: UITableViewDelegate, UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.rooms.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "BorrowTableViewCell", for: indexPath) as! BorrowTableViewCell
        cell.configure(roomInfo: self.rooms[indexPath.row])
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        let detail = self.viewController(identifier: "BorrowDetailViewController") as! BorrowDetailViewController
        detail.set(roomInfo: self.rooms[indexPath.row])
        self.tabbarViewController()?.stack(viewController: detail, animationType: .horizontal)
    }
}

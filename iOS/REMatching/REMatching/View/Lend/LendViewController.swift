//
//  LendViewController.swift
//  REMatching
//
//  Created by Leapfrog-Software on 2018/06/23.
//  Copyright © 2018年 Leapfrog-Inc. All rights reserved.
//

import UIKit

class LendViewController: UIViewController {
    
    @IBOutlet private weak var tableView: UITableView!
    @IBOutlet private weak var noDataLabel: UILabel!

    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.tableView.rowHeight = UITableViewAutomaticDimension
        self.tableView.estimatedRowHeight = 140
        self.reloadTable()
    }
    
    func reloadTable() {
        self.tableView.reloadData()
        self.noDataLabel.isHidden = !SaveData.shared.createdRoomIds.isEmpty
    }
    
    @IBAction func onTapLend(_ sender: Any) {
        let create = self.viewController(identifier: "CreateLendViewController") as! CreateLendViewController
        create.completion = {
            self.reloadTable()
        }
        self.tabbarViewController()?.stack(viewController: create, animationType: .horizontal)
    }
}

extension LendViewController: UITableViewDelegate, UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return SaveData.shared.createdRoomIds.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "LendTableViewCell", for: indexPath) as! LendTableViewCell
        if let roomData = (RoomRequester.shared.dataList.filter { $0.id == SaveData.shared.createdRoomIds[indexPath.row] }).first {
            cell.configure(roomData: roomData)
        }
        return cell
    }
}

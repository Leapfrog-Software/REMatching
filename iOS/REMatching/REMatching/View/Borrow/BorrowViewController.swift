//
//  BorrowViewController.swift
//  REMatching
//
//  Created by Leapfrog-Software on 2018/06/23.
//  Copyright © 2018年 Leapfrog-Inc. All rights reserved.
//

import UIKit

class BorrowViewController: UIViewController {
    
    @IBOutlet private weak var tableView: UITableView!
    @IBOutlet private weak var noDataLabel: UILabel!
    
    private var rooms = [RoomData]()
    private var searchIndex = 0

    override func viewDidLoad() {
        super.viewDidLoad()

        self.tableView.rowHeight = UITableViewAutomaticDimension
        self.tableView.estimatedRowHeight = 280
        
        self.reloadTable()
    }
    
    @IBAction func onTapSearch(_ sender: Any) {
        let search = self.viewController(identifier: "SearchViewController") as! SearchViewController
        search.set(defaultIndex: self.searchIndex, completion: { index in
            self.searchIndex = index
            self.reloadTable()
        })
        self.tabbarViewController()?.stack(viewController: search, animationType: .vertical)
    }
    
    func reloadTable() {
        
        if searchIndex == 0 {
            self.rooms = RoomRequester.shared.approvedRooms()
        } else {
            let pref = SearchViewController.prefs[self.searchIndex]
            self.rooms = RoomRequester.shared.approvedRooms().filter { $0.place.contains(pref) }
        }
    
        self.tableView.reloadData()
        self.noDataLabel.isHidden = !self.rooms.isEmpty
    }
}

extension BorrowViewController: UITableViewDelegate, UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.rooms.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "BorrowTableViewCell", for: indexPath) as! BorrowTableViewCell
        cell.configure(roomData: self.rooms[indexPath.row])
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        let detail = self.viewController(identifier: "BorrowDetailViewController") as! BorrowDetailViewController
        detail.set(roomData: self.rooms[indexPath.row])
        self.tabbarViewController()?.stack(viewController: detail, animationType: .horizontal)
    }
}

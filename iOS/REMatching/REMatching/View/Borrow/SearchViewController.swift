//
//  SearchViewController.swift
//  REMatching
//
//  Created by Leapfrog-Software on 2018/06/26.
//  Copyright © 2018年 Leapfrog-Inc. All rights reserved.
//

import UIKit

class SearchViewController: UIViewController {
    
    static let prefs = [
        "指定なし", "北海道", "青森県", "岩手県", "宮城県", "秋田県", "山形県", "福島県",
        "茨城県", "栃木県", "群馬県", "埼玉県", "千葉県", "東京都", "神奈川県", "新潟県",
        "富山県", "石川県", "福井県", "山梨県", "長野県", "岐阜県", "静岡県", "愛知県",
        "三重県", "滋賀県", "京都府", "大阪府", "兵庫県", "奈良県", "和歌山県", "鳥取県",
        "島根県", "岡山県", "広島県", "山口県", "徳島県", "香川県", "愛媛県", "高知県",
        "福岡県", "佐賀県", "長崎県", "熊本県", "大分県", "宮崎県", "鹿児島県", "沖縄県"
    ]
    
    private var selectedIndex = 0
    private var completion: ((Int) -> ())?
    
    func set(defaultIndex: Int, completion: @escaping ((Int) -> ())) {
        self.selectedIndex = defaultIndex
        self.completion = completion
    }
    
    private func close() {
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
            self.pop(animationType: .vertical)
        }
    }
    
    @IBAction func onTapClose(_ sender: Any) {
        self.pop(animationType: .vertical)
    }
}

extension SearchViewController: UITableViewDelegate, UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return SearchViewController.prefs.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "SearchTableViewCell", for: indexPath) as! SearchTableViewCell
        cell.configure(name: SearchViewController.prefs[indexPath.row], selected: self.selectedIndex == indexPath.row)
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {

        self.selectedIndex = indexPath.row
        tableView.reloadData()

        self.completion?(indexPath.row)
        self.close()
    }
}

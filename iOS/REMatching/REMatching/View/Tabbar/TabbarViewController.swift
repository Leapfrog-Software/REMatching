//
//  TabbarViewController.swift
//  REMatching
//
//  Created by Leapfrog-Software on 2018/06/23.
//  Copyright © 2018年 Leapfrog-Inc. All rights reserved.
//

import UIKit

class TabbarViewController: UIViewController {

    @IBOutlet private weak var containerView: UIView!
    @IBOutlet private weak var tab1ImageView: UIImageView!
    @IBOutlet private weak var tab2ImageView: UIImageView!
    @IBOutlet private weak var tab1Label: UILabel!
    @IBOutlet private weak var tab2Label: UILabel!
    
    private var borrowViewController: BorrowViewController?
    private var lendViewController: LendViewController?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let borrowViewController = self.viewController(identifier: "BorrowViewController") as! BorrowViewController
        self.addContent(view: borrowViewController.view)
        self.borrowViewController = borrowViewController
        
        let lendViewController = self.viewController(identifier: "LendViewController") as! LendViewController
        self.addContent(view: lendViewController.view)
        self.lendViewController = lendViewController
        
        self.changeTab(index: 0)
    }
    
    private func addContent(view: UIView) {
        
        self.containerView.addSubview(view)
        view.translatesAutoresizingMaskIntoConstraints = false
        view.topAnchor.constraint(equalTo: self.containerView.topAnchor).isActive = true
        view.leadingAnchor.constraint(equalTo: self.containerView.leadingAnchor).isActive = true
        view.trailingAnchor.constraint(equalTo: self.containerView.trailingAnchor).isActive = true
        view.bottomAnchor.constraint(equalTo: self.containerView.bottomAnchor).isActive = true
    }
    
    private func changeTab(index: Int) {
        
        self.tab1ImageView.image = UIImage(named: (index == 0) ? "icon_borrow_selected" : "icon_borrow")
        self.tab1Label.textColor = (index == 0) ? UIColor(red: 30 / 255, green: 30 / 255, blue: 30 / 255, alpha: 1) : UIColor(red: 180 / 255, green: 180 / 255, blue: 180 / 255, alpha: 1)
        
        self.tab2ImageView.image = UIImage(named: (index == 1) ? "icon_lend_selected" : "icon_lend")
        self.tab2Label.textColor = (index == 1) ? UIColor(red: 30 / 255, green: 30 / 255, blue: 30 / 255, alpha: 1) : UIColor(red: 180 / 255, green: 180 / 255, blue: 180 / 255, alpha: 1)
        
        self.borrowViewController?.view.isHidden = (index != 0)
        self.lendViewController?.view.isHidden = (index != 1)
    }
    
    @IBAction func onTapBorrow(_ sender: Any) {
        self.changeTab(index: 0)
    }

    @IBAction func onTapLend(_ sender: Any) {
        self.changeTab(index: 1)
    }
}

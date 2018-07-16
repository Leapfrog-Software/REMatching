//
//  LendViewController.swift
//  REMatching
//
//  Created by Leapfrog-Software on 2018/06/23.
//  Copyright © 2018年 Leapfrog-Inc. All rights reserved.
//

import UIKit

class LendViewController: UIViewController {

    @IBAction func onTapLend(_ sender: Any) {
        let create = self.viewController(identifier: "CreateLendViewController") as! CreateLendViewController
        self.tabbarViewController()?.stack(viewController: create, animationType: .horizontal)
    }
}

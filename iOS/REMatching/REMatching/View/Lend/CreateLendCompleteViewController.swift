//
//  CreateLendCompleteViewController.swift
//  REMatching
//
//  Created by Leapfrog-Software on 2018/06/26.
//  Copyright © 2018年 Leapfrog-Inc. All rights reserved.
//

import UIKit

class CreateLendCompleteViewController: UIViewController {

    @IBAction func onTapClose(_ sender: Any) {
        
        (self.parent?.parent as? LendViewController)?.reloadTable()
        self.parent?.pop(animationType: .horizontal)
    }
}

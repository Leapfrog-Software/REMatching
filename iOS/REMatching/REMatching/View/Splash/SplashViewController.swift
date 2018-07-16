//
//  SplashViewController.swift
//  REMatching
//
//  Created by Leapfrog-Software on 2018/06/23.
//  Copyright © 2018年 Leapfrog-Inc. All rights reserved.
//

import UIKit

class SplashViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
            self.fetch()
        }
    }
    
    private func fetch() {
        RoomRequester.shared.fetch(completion: { result in
            if result {
                self.stackTabbar()
            } else {
                let alert = UIAlertController(title: "エラー", message: "通信に失敗しました", preferredStyle: .alert)
                let action = UIAlertAction(title: "リトライ", style: .default, handler: { _ in
                    self.fetch()
                })
                alert.addAction(action)
                self.present(alert, animated: true, completion: nil)
            }
        })
    }
    
    private func stackTabbar() {
        
        let blackView = UIView()
        blackView.backgroundColor = .black
        blackView.alpha = 0
        self.view.addSubview(blackView)
        blackView.translatesAutoresizingMaskIntoConstraints = false
        blackView.topAnchor.constraint(equalTo: self.view.topAnchor).isActive = true
        blackView.leadingAnchor.constraint(equalTo: self.view.leadingAnchor).isActive = true
        blackView.trailingAnchor.constraint(equalTo: self.view.trailingAnchor).isActive = true
        blackView.bottomAnchor.constraint(equalTo: self.view.bottomAnchor).isActive = true
        
        UIView.animate(withDuration: 0.2, animations: {
            blackView.alpha = 1.0
        }, completion: { [weak self] _ in
            let tabbarViewController = self?.viewController(identifier: "TabbarViewController") as! TabbarViewController
            self?.stack(viewController: tabbarViewController, animationType: .none)
            self?.view.bringSubview(toFront: blackView)
            
            UIView.animate(withDuration: 0.2, animations: {
                blackView.alpha = 0
            }, completion: { _ in
                blackView.removeFromSuperview()
            })
        })
    }
}

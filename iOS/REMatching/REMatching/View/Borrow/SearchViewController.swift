//
//  SearchViewController.swift
//  REMatching
//
//  Created by Leapfrog-Software on 2018/06/26.
//  Copyright © 2018年 Leapfrog-Inc. All rights reserved.
//

import UIKit

class SearchViewController: UIViewController {

    @IBOutlet private weak var area1ImageView: UIImageView!
    @IBOutlet private weak var area2ImageView: UIImageView!
    @IBOutlet private weak var area3ImageView: UIImageView!
    @IBOutlet private weak var area4ImageView: UIImageView!
    
    private var defaultIndex = 0
    private var completion: ((Int) -> ())?
    
    func set(defaultIndex: Int, completion: @escaping ((Int) -> ())) {
        self.defaultIndex = defaultIndex
        self.completion = completion
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.change(index: self.defaultIndex)
    }
    
    private func change(index: Int) {
        self.area1ImageView.isHidden = index != 0
        self.area2ImageView.isHidden = index != 1
        self.area3ImageView.isHidden = index != 2
        self.area4ImageView.isHidden = index != 3
        
        self.completion?(index)
    }
    
    private func close() {
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
            self.pop(animationType: .vertical)
        }
    }
    
    @IBAction func onTap1(_ sender: Any) {
        self.change(index: 0)
        self.close()
    }
    
    @IBAction func onTap2(_ sender: Any) {
        self.change(index: 1)
        self.close()
    }
    
    @IBAction func onTap3(_ sender: Any) {
        self.change(index: 2)
        self.close()
    }
    
    @IBAction func onTap4(_ sender: Any) {
        self.change(index: 3)
        self.close()
    }

    @IBAction func onTapClose(_ sender: Any) {
        self.pop(animationType: .vertical)
    }
}

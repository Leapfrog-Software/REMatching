//
//  CreateLendViewController.swift
//  REMatching
//
//  Created by Leapfrog-Software on 2018/06/23.
//  Copyright © 2018年 Leapfrog-Inc. All rights reserved.
//

import UIKit

class CreateLendViewController: UIViewController {
    
    @IBOutlet private weak var roomImageView: UIImageView!

    @IBAction func onTapImage(_ sender: Any) {
        let pickerView = UIImagePickerController()
        pickerView.sourceType = .photoLibrary
        pickerView.delegate = self
        self.present(pickerView, animated: true)
    }
    
    @IBAction func didEndEdit(_ sender: Any) {
        self.view.endEditing(true)
    }
    
    @IBAction func onTapBack(_ sender: Any) {
        self.pop(animationType: .horizontal)
    }
    
    @IBAction func onTapCreate(_ sender: Any) {
        let complete = self.viewController(identifier: "CreateLendCompleteViewController") as! CreateLendCompleteViewController
        self.stack(viewController: complete, animationType: .horizontal)
    }
}

extension CreateLendViewController: UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : Any]) {
        let image = info[UIImagePickerControllerOriginalImage] as! UIImage
        self.roomImageView.image = image
        picker.dismiss(animated: true, completion: nil)
    }
}

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
    @IBOutlet private weak var nameTextField: UITextField!
    @IBOutlet private weak var placeTextField: UITextField!
    @IBOutlet private weak var rentTextField: UITextField!
    @IBOutlet private weak var phoneTextField: UITextField!
    @IBOutlet private weak var emailTextField: UITextField!
    
    private var roomImage: UIImage?
    
    private func showError(message: String) {
        let alert = UIAlertController(title: "エラー", message: message, preferredStyle: .alert)
        let action = UIAlertAction(title: "OK", style: .default, handler: nil)
        alert.addAction(action)
        self.present(alert, animated: true, completion: nil)
    }
    
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
        
        let name = self.nameTextField.text ?? ""
        let place = self.placeTextField.text ?? ""
        let rent = self.rentTextField.text ?? ""
        let phone = self.phoneTextField.text ?? ""
        let email = self.emailTextField.text ?? ""
        
        if name.count == 0 {
            self.showError(message: "物件名を入力してください")
            return
        }
        if place.count == 0 {
            self.showError(message: "住所を入力してください")
            return
        }
        if rent.count == 0 {
            self.showError(message: "賃料を入力してください")
            return
        }
        if Int(rent) == nil {
            self.showError(message: "賃料が正しくありません")
            return
        }
        if phone.count == 0 && email.count == 0 {
            self.showError(message: "電話番号・Emailのいずれかを入力してください")
            return
        }
        
        Loading.start()

        RoomRequester.post(name: name, place: place, rent: Int(rent) ?? 0, phone: phone, email: email, completion: { result, roomId in
            
            if result, let id = roomId {
                self.imageUpload(roomId: id)
                
                let saveData = SaveData.shared
                saveData.createdRoomIds.append(id)
                saveData.save()
                
            } else {
                Loading.stop()
                self.showError(message: "通信に失敗しました")
            }
        })
    }
    
    private func imageUpload(roomId: String) {
        
        if let image = self.roomImage {
            let params = [
                "command": "uploadRoomImage",
                "roomId": roomId
            ]
            ImageUploader.post(url: Constants.ServerApiUrl, image: image, params: params, completion: { result, _ in
                if result {
                    self.fetchRoom()
                } else {
                    Loading.stop()
                    self.showError(message: "通信に失敗しました")
                }
            })
        } else {
            Loading.stop()
            self.stackComplete()
        }
    }
    
    private func fetchRoom() {
        RoomRequester.shared.fetch(completion: { _ in
            Loading.stop()
            self.stackComplete()
        })
    }
    
    private func stackComplete() {
        let complete = self.viewController(identifier: "CreateLendCompleteViewController") as! CreateLendCompleteViewController
        self.stack(viewController: complete, animationType: .horizontal)
    }
}

extension CreateLendViewController: UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : Any]) {
        let image = info[UIImagePickerControllerOriginalImage] as! UIImage
        
        if let cropedImage = image.crop(size: CGSize(width: 240, height: 240)) {
            self.roomImageView.image = cropedImage
            self.roomImage = cropedImage
            picker.dismiss(animated: true, completion: nil)
        }
    }
}

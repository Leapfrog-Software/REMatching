//
//  RoomRequester.swift
//  REMatching
//
//  Created by Leapfrog-Software on 2018/07/16.
//  Copyright © 2018年 Leapfrog-Inc. All rights reserved.
//

import Foundation

struct RoomData {
    
    let id: String
    let isChecked: Bool
    let score: Int
    var review: Int
    var name: String
    var place: String
    var rent: Int
    var phone: String
    var email: String
    
    init?(data: Dictionary<String, Any>) {
        
        guard let roomId = data["id"] as? String else {
            return nil
        }
        self.id = roomId
        
        self.isChecked = (data["isChecked"] as? String) == "1"
        self.score = Int(data["score"] as? String ?? "0") ?? 0
        self.review = Int(data["review"] as? String ?? "0") ?? 0
        self.name = (data["name"] as? String)?.base64Decode() ?? ""
        self.place = (data["place"] as? String)?.base64Decode() ?? ""
        self.rent = Int(data["rent"] as? String ?? "0") ?? 0
        self.phone = (data["phone"] as? String)?.base64Decode() ?? ""
        self.email = (data["email"] as? String)?.base64Decode() ?? ""
    }
}

class RoomRequester {
    
    static let shared = RoomRequester()
    
    var dataList = [RoomData]()
    
    func fetch(completion: @escaping ((Bool) -> ())) {
        
        let params = [
            "command": "getRoom"
        ]
        ApiManager.post(params: params) { [weak self] result, data in
            if result, let dic = data as? Dictionary<String, Any> {
                if dic["result"] as? String == "0", let datas = dic["data"] as? Array<Dictionary<String, Any>> {
                    self?.dataList = datas.compactMap { RoomData(data: $0) }
                    completion(true)
                    return
                }
            }
            completion(false)
        }
    }
    
    func checkedRooms() -> [RoomData] {
        return self.dataList.filter { $0.isChecked == true }
    }
    
    class func post(name: String, place: String, rent: Int, phone: String, email: String, completion: @escaping ((Bool, String?) -> ())) {
        
        let params = [
            "command": "postRoom",
            "name": name.base64Encode() ?? "",
            "place": place.base64Encode() ?? "",
            "rent": "\(rent)",
            "phone": phone.base64Encode() ?? "",
            "email": email.base64Encode() ?? ""
        ]
        ApiManager.post(params: params) { (result, data) in
            if result, let dic = data as? Dictionary<String, Any> {
                if dic["result"] as? String == "0", let roomId = dic["roomId"] as? String {
                    completion(true, roomId)
                    return
                }
            }
            completion(false, nil)            
        }
    }

}

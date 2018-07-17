//
//  SaveData.swift
//  REMatching
//
//  Created by Leapfrog-Software on 2018/07/17.
//  Copyright © 2018年 Leapfrog-Inc. All rights reserved.
//

import Foundation

class SaveData {
    
    static let shared = SaveData()
    
    var createdRoomIds = [String]()
    
    init() {
        let userDefaults = UserDefaults()
        self.createdRoomIds = userDefaults.array(forKey: Constants.UserDefaultKey.CreatedRoomIds) as? [String] ?? []
    }
    
    func save() {
        
        let userDefaults = UserDefaults()
        
        userDefaults.set(self.createdRoomIds, forKey: Constants.UserDefaultKey.CreatedRoomIds)
        
        userDefaults.synchronize()
    }
}

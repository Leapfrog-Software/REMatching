//
//  Constants.swift
//  REMatching
//
//  Created by Leapfrog-Software on 2018/07/17.
//  Copyright © 2018年 Leapfrog-Inc. All rights reserved.
//

import Foundation

struct Constants {
    static let ServerRootUrl = "http://localhost/REMatching/"
    static let ServerApiUrl = Constants.ServerRootUrl + "srv.php"
    static let RoomImageDirectory = Constants.ServerRootUrl + "data/image/room/"
    static let HttpTimeOutInterval = TimeInterval(10)
    static let StringEncoding = String.Encoding.utf8
    
    struct UserDefaultKey {
        static let CreatedRoomIds = "CreatedRoomIds"
    }
}

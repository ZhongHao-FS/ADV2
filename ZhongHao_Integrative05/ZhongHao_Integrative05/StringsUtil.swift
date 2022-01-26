//
//  StringsUtil.swift
//  ZhongHao_Integrative05
//
//  Created by Hao Zhong on 1/23/22.
//

import Foundation

class StringsUtil {
    public static func isEmailValid(email: String) -> Bool {
        if let dot = email.lastIndex(of: "."), let atar = email.firstIndex(of: "@") {
            if dot < atar {
                return false
            }
            if atar != email.lastIndex(of: "@") {
                return false
            }
            if dot == email.index(before: email.endIndex) {
                return false
            }
            if atar == email.startIndex {
                return false
            }
        } else {
            return false
        }
        
        let parts: [String.SubSequence] = email.split(separator: "@")
        if parts[0].trimmingCharacters(in: NSCharacterSet.whitespacesAndNewlines).count == 0 {
            return false
        }
        if parts[0].last == "." {
            return false
        }
        if parts[0].starts(with: ".") {
            return false
        }
        if parts[1].starts(with: ".") {
            return false
        }
        
        let chars: [Character] = [Character](email)
        for c in chars {
            if !c.isLetter && !c.isNumber && c != "@" && c != "." {
                return false
            }
        }
        
        return true
    }
}

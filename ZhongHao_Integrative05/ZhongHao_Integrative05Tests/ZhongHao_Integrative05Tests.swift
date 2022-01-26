//
//  ZhongHao_Integrative05Tests.swift
//  ZhongHao_Integrative05Tests
//
//  Created by Hao Zhong on 1/23/22.
//

import XCTest
@testable import ZhongHao_Integrative05

class ZhongHao_Integrative05Tests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testExample() throws {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
        XCTAssert(StringsUtil.isEmailValid(email: "hello@example.com"))
        XCTAssert(StringsUtil.isEmailValid(email: "a.a.a@a.a.a"))
        XCTAssert(StringsUtil.isEmailValid(email: "a@a.a"))
        
        XCTAssert(!StringsUtil.isEmailValid(email: ".@.a"))
        XCTAssert(!StringsUtil.isEmailValid(email: "@a.a"))
        XCTAssert(!StringsUtil.isEmailValid(email: "hello.@a.a"))
        XCTAssert(!StringsUtil.isEmailValid(email: "!@#*.*&"))
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}

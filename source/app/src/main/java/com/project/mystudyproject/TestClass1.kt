package com.project.mystudyproject

/**
 * @ClassName: TestClass1
 * @Author: zyf
 * @Date: 2021/3/25 11:17
 * @Description: 作用描述
 * @update: 更新者和更新内容
 */
sealed class TestClass1 {

    constructor(name: String)

    constructor(age: Int)
}

object Test : TestClass1(213) {

}

object Idle: TestClass1("21321"){

}

class Playing(name: String): TestClass1(name){

}
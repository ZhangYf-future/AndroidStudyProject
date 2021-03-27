package com.project.mystudyproject.network

/**
 * @ClassName: Person
 * @Author: zyf
 * @Date: 2021/3/24 9:13
 * @Description: 作用描述
 * @update: 更新者和更新内容
 */
class Person
@JvmOverloads
constructor(var age: Int, var name: String = "zyf") {
    internal var email: String? = null
        private set
        internal get

    @JvmName("$21321321")
    internal fun person() {
        println("PERSON")
    }

}
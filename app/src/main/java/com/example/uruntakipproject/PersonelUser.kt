package com.example.uruntakipproject

class PersonelUser {
    var id: Int =0
    var name:String=""
    var birim:Int=0
    var gelenCode:String=""

    constructor(id: Int, name: String, birim: Int, gelenCode: String) {
        this.id = id
        this.name = name
        this.birim = birim
        this.gelenCode = gelenCode
    }
}
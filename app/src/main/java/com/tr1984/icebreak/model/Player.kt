package com.tr1984.icebreak.model

class Player {
    var uid: String = ""
    var color: String = "#000000"
    var x: Float = 0f
    var y: Float = 0f

    constructor()

    constructor(uid: String, color: String, x: Float, y: Float) {
        this.uid = uid
        this.color = color
        this.x = x
        this.y = y
    }
}
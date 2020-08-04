package com.zakia.idn.chattingapp.model

class ChatList {
    private var id : String = ""

    constructor()

    constructor(id: String) {
        this.id = id
    }
    //getter and setter id user

    fun getId(): String? {
        return id
    }
    fun setId (id: String){
        this.id = id!!
    }
}
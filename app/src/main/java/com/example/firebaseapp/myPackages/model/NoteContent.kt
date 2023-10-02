package com.example.firebaseapp.myPackages.model


class NoteContent{
    var id:String?=null
    var title:String?=null
    var note:String?=null
    var date:String?=null
    var email:String?=null
    var password:String?=null
    constructor(){

    }
    constructor(  id:String,title:String, note:String, date: String,email:String,password:String)
    {
        this.id=id
        this.title=title
        this.note=note
        this.date=date
        this.email=email
        this.password=password
    }
}
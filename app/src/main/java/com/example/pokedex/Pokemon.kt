package com.example.pokedex

class Pokemon {
    val img: String
    val name: String
    val type: String
    val dateCatch:String
    val defense: String
    val attack: String
    val speed: String
    val life: String

    constructor(img:String,name: String,type: String,dateCatch:String,defense: String,attack: String,speed: String,life: String){
        this.img = img
        this.name = name
        this.type = type
        this.dateCatch = dateCatch
        this.defense = defense
        this.attack = attack
        this.speed = speed
        this.life = life
    }
}
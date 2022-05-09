package com.example.pokedex.model

import android.graphics.Bitmap
import java.io.Serializable

data class Pokemon (
    val username:String = "",
    val img: String = "",
    val name: String = "",
    val type: String = "",
    val dateCatch: Long = 0,
    val defense: String = "",
    val attack: String = "",
    val speed: String = "",
    val life: String = "",
    ):Serializable
    /*constructor(img:String,name: String,type: String,dateCatch:String,defense: String,attack: String,speed: String,life: String){
        this.img = img
        this.name = name
        this.type = type
        this.dateCatch = dateCatch
        this.defense = defense
        this.attack = attack
        this.speed = speed
        this.life = life
    }*/